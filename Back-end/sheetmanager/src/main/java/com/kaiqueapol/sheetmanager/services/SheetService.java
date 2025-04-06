package com.kaiqueapol.sheetmanager.services;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.sheetmanager.util.CopyPasteRow;
import com.kaiqueapol.sheetmanager.util.ZipSheet;
import com.kaiqueapol.sheetmanager.validations.ValidateAndConvertSheet;

@Service
public class SheetService {
	private ValidateAndConvertSheet validateAndConvert;
	private ZipSheet zipSheet;
	private CopyPasteRow copyPasteRow;

	public SheetService(ValidateAndConvertSheet validateAndConvert, ZipSheet zipSheet) {
		this.validateAndConvert = validateAndConvert;
		this.zipSheet = zipSheet;
	}

	public void divideSheets(MultipartFile rawFile, int sheetParts, boolean header) throws Exception {
		File file = validateAndConvert.sheetValidationAndConvertion(rawFile);

		// How many sheets do you want the old sheet to be divided in?
		int amountOfNewSheets = sheetParts;

		// Get first/desired sheet from the workbook
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		int amountOfRowsInOriginalSheet = sheet.getPhysicalNumberOfRows();
		int amountOfRowsInNewSheets = amountOfRowsInOriginalSheet / amountOfNewSheets;

		// Here, it is create new workbooks with new sheets
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
			// If tests if the limit established for each new sheet was reached
			if (rowNumber == amountOfRowsInNewSheets && selectedSheet < amountOfNewSheets - 1) {
				// If so, it selects another sheet and reset the row counter
				selectedSheet++;
				rowNumber = 0;

				// If you want the first line to be the same in every sheet?
				if (header) {
					Row headerRow = sheet.getRow(sheet.getFirstRowNum());
					copyPasteRow.copyPasteRow(headerRow, listOfNewSheets[selectedSheet], rowNumber++);
				}
			}

			copyPasteRow.copyPasteRow(row, listOfNewSheets[selectedSheet], rowNumber++);

		}
		// Save new workbooks
		zipSheet.sheetZipping(amountOfNewSheets, listOfNewWorkbook, workbook);
	}

}
