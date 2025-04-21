package com.kaiqueapol.gridlab.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.exceptions.FileEntityNotFoundException;
import com.kaiqueapol.gridlab.repositories.FileRepository;
import com.kaiqueapol.gridlab.util.CopyPasteRow;
import com.kaiqueapol.gridlab.util.ZipSheet;
import com.kaiqueapol.gridlab.validations.FileValidation;
import com.kaiqueapol.gridlab.validations.RowPerSheetsValidation;

@Service
public class SheetDividerService {
	private FileValidation fileValidation;
	private ZipSheet zipSheet;
	private CopyPasteRow copyPasteRow;
	private FileRepository fileRep;
	private RowPerSheetsValidation wpsvalid;

	public SheetDividerService(FileValidation fileValidation, ZipSheet zipSheet, CopyPasteRow copyPasteRow,
			FileRepository fileRep, RowPerSheetsValidation wpsvalid) {
		this.fileValidation = fileValidation;
		this.zipSheet = zipSheet;
		this.copyPasteRow = copyPasteRow;
		this.fileRep = fileRep;
		this.wpsvalid = wpsvalid;
	}

	public FileEntity divideSheets(MultipartFile rawFile, int amountOfNewSheets, boolean header) throws Exception {
		// It validates if the file is a .xlsx or .xls sheet and converts it to
		// File
		MultipartFile file = fileValidation.sheetValidation(rawFile);

		// Get first/desired sheet from the workbook
		Workbook workbook = null;
		if (file.getOriginalFilename().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(file.getInputStream());
		} else if (file.getOriginalFilename().endsWith(".xls")) {
			workbook = new HSSFWorkbook(file.getInputStream());
		}
		Sheet sheet = workbook.getSheetAt(0);

		int amountOfRowsInOriginalSheet = sheet.getPhysicalNumberOfRows();
		int amountOfRowsInNewSheets = amountOfRowsInOriginalSheet / amountOfNewSheets;
		wpsvalid.rowPerSheetsValidation(amountOfRowsInOriginalSheet, amountOfNewSheets);

		// Here, it is create new workbooks with a new sheet
		Workbook[] listOfNewWorkbook = new Workbook[amountOfNewSheets];
		Sheet[] listOfNewSheets = new Sheet[amountOfNewSheets];
		for (int i = 0; i < amountOfNewSheets; i++) {
			listOfNewWorkbook[i] = new XSSFWorkbook();
			listOfNewSheets[i] = listOfNewWorkbook[i].createSheet("Sheet1");
		}

		int rowNumber = 0;
		int selectedSheet = 0;

		// It'll take each line from the original sheet at a time
		for (Row row : sheet) {
			// If tests if the limit established for each new sheet has reached
			if (rowNumber == amountOfRowsInNewSheets && selectedSheet < amountOfNewSheets - 1) {
				// If so, then it selects another sheet and reset the row counter
				selectedSheet++;
				rowNumber = 0;

				// If the user wants the first line to be the same in every sheet
				if (header) {
					Row headerRow = sheet.getRow(sheet.getFirstRowNum());
					copyPasteRow.copyPasteRow(headerRow, listOfNewSheets[selectedSheet], rowNumber++);
				}
			}
			copyPasteRow.copyPasteRow(row, listOfNewSheets[selectedSheet], rowNumber++);
		}
		// Save the new sheets into a .zip file
		String fileNameWoExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 5);
		zipSheet.sheetZipping(fileNameWoExtension, amountOfNewSheets, listOfNewWorkbook, workbook);
		System.out.println("HERE");
		FileEntity fileEntity = zipToEntity(new File("UploadFolder\\" + fileNameWoExtension + ".zip"));
		System.out.println("DONE");
		return fileEntity;
	}

	public FileEntity zipToEntity(File zip) throws IOException {
		FileEntity fileEntity = new FileEntity(UUID.randomUUID(), zip.getName(), zip.getTotalSpace(),
				Files.probeContentType(zip.toPath()), Files.readAllBytes(zip.toPath()));
		fileRep.save(fileEntity);
		return fileEntity;
	}

	public Resource downloadZip(UUID id) {
		ByteArrayResource resource = new ByteArrayResource(getEntityById(id).getData());
		return resource;

	}

	public FileEntity getEntityById(UUID id) {
		FileEntity foundFile = Optional.ofNullable(fileRep.getFileEntityById(id))
				.orElseThrow(FileEntityNotFoundException::new);

		return foundFile;
	}
}
