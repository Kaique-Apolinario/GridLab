package com.kaiqueapol.sheetmanager.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.sheetmanager.entities.FileEntity;
import com.kaiqueapol.sheetmanager.repositories.FileRepository;
import com.kaiqueapol.sheetmanager.util.CopyPasteRow;
import com.kaiqueapol.sheetmanager.util.ZipSheet;
import com.kaiqueapol.sheetmanager.validations.ValidateAndConvertSheet;

@Service
public class SheetService {
	private ValidateAndConvertSheet validateAndConvert;
	private ZipSheet zipSheet;
	private CopyPasteRow copyPasteRow;
	private FileRepository fileRep;

	public SheetService(ValidateAndConvertSheet validateAndConvert, ZipSheet zipSheet, CopyPasteRow copyPasteRow,
			FileRepository fileRep) {
		this.validateAndConvert = validateAndConvert;
		this.zipSheet = zipSheet;
		this.copyPasteRow = copyPasteRow;
		this.fileRep = fileRep;
	}

	public FileEntity divideSheets(MultipartFile rawFile, int sheetParts, boolean header, boolean saveFile)
			throws Exception {

		// It validates if the file is a .xlsx or .xls sheet and converts it to
		// File
		File file = validateAndConvert.sheetValidationAndConvertion(rawFile);

		// How many sheets the user wants the old sheet to be divided in
		int amountOfNewSheets = sheetParts;

		// Get first/desired sheet from the workbook
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		int amountOfRowsInOriginalSheet = sheet.getPhysicalNumberOfRows();
		int amountOfRowsInNewSheets = amountOfRowsInOriginalSheet / amountOfNewSheets;

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
		String fileNameWoExtension = rawFile.getOriginalFilename().substring(0,
				rawFile.getOriginalFilename().length() - 5);
		zipSheet.sheetZipping(fileNameWoExtension, amountOfNewSheets, listOfNewWorkbook, workbook);
		FileEntity fileEntity = zipToEntity(new File("UploadFolder\\SheetZip.zip"));
		return fileEntity;
	}

	public FileEntity zipToEntity(File zip) throws IOException {
		FileEntity fileEntity = new FileEntity(UUID.randomUUID(), zip.getName(), zip.getTotalSpace(),
				Files.probeContentType(zip.toPath()), Files.readAllBytes(zip.toPath()));
		fileRep.save(fileEntity);
		return fileEntity;
	}

	public Resource downloadZip(UUID id) {
		FileEntity foundFileEntity = getEntityById(id);
		ByteArrayResource resource = new ByteArrayResource(foundFileEntity.getData());
		return resource;

	}

	public FileEntity getEntityById(UUID id) {
		return fileRep.getFileEntityById(id);
	}
}
