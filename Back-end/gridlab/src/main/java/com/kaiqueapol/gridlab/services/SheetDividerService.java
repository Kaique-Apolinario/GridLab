package com.kaiqueapol.gridlab.services;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.util.CopyPasteRow;
import com.kaiqueapol.gridlab.util.ZipSheet;
import com.kaiqueapol.gridlab.validations.FileValidation;
import com.kaiqueapol.gridlab.validations.RowPerSheetsValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SheetDividerService {
	private DownloadZipService zipServ;

	public FileEntity divideSheets(MultipartFile rawFile, int amountOfNewSheets, boolean header) throws Exception {
		// It validates if the file is a .xlsx or .xls sheet and converts it to
		// File
		MultipartFile file = FileValidation.sheetValidation(rawFile);

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
		RowPerSheetsValidation.rowPerSheetsValidation(amountOfRowsInOriginalSheet, amountOfNewSheets);

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
					CopyPasteRow.copyPasteRow(headerRow, listOfNewSheets[selectedSheet], rowNumber++);
				}
			}
			CopyPasteRow.copyPasteRow(row, listOfNewSheets[selectedSheet], rowNumber++);
		}
		// Save the new sheets into a .zip file
		String fileNameWoExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 5);
		ZipSheet.sheetZipping(fileNameWoExtension, amountOfNewSheets, listOfNewWorkbook, workbook);

		FileEntity fileEntity = zipServ
				.zipToEntity(new File(System.getProperty("java.io.tmpdir") + fileNameWoExtension + ".zip"));
		return fileEntity;
	}

}
