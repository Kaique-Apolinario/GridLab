package com.kaiqueapol.gridlab.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.util.ZipSheet;
import com.kaiqueapol.gridlab.validations.FileValidation;

@Service
public class SheetMergerService {

	public SheetMergerService() {
	}

	public FileEntity mergeSheets(List<MultipartFile> listOfSheets, boolean ignoreRepeatedRows)
			throws IOException, InvalidFormatException {

		for (MultipartFile rawFile : listOfSheets) {
			FileValidation.sheetValidation(rawFile);
		}

		Set<String> uniqueRows = new HashSet<>();

		// return type of listFiles is array
		List<MultipartFile> validatedFiles = new ArrayList<>();
		// Print name of the all files present in that path
		if (listOfSheets != null) {
			for (MultipartFile file : listOfSheets) {
				validatedFiles.add(FileValidation.sheetValidation(file));
			}
		}

		Workbook unitionWorkbook = new XSSFWorkbook();
		unitionWorkbook.createSheet("unition sheet");

		List<Workbook> ogFilesWorkbooks = new ArrayList<>();

		for (MultipartFile file : validatedFiles) {

			if (file.getOriginalFilename().endsWith(".xlsx")) {
				XSSFWorkbook fileWorkbook = new XSSFWorkbook(file.getInputStream());
				ogFilesWorkbooks.add(fileWorkbook);
			} else if (file.getOriginalFilename().endsWith(".xls")) {
				Workbook fileWorkbook = new HSSFWorkbook(file.getInputStream());
				ogFilesWorkbooks.add(fileWorkbook);
			}
		}
		int usRowNumber = 0;
		for (Workbook originalSheet : ogFilesWorkbooks) { // For each original sheet

			for (Row row : originalSheet.getSheetAt(0)) { // I'll read its row

				Iterator<Cell> cellIterator = row.cellIterator(); // And will create a iterator of cells based on the
																	// original sheet row
				if (ignoreRepeatedRows) {
					// Tranform original row into a string
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < row.getLastCellNum(); i++) {
						Cell cell = row.getCell(i);
						sb.append(cell == null ? "null" : cell.toString()).append("|");
					}
					String rowToString = sb.toString();

					// If ignoreRepeatedRows is true and this string already exists in the list,
					// skip it

					if (ignoreRepeatedRows && uniqueRows.contains(rowToString)) {
						continue;
					} else {
						uniqueRows.add(rowToString);
					}

				}

				// Then will create a new row in the unition sheet
				Row rowOfunitionSheet = unitionWorkbook.getSheet("unition sheet").createRow(usRowNumber++);

				int cellNum = 0;
				while (cellIterator.hasNext()) { // It will read the original cells cell by cell
					Cell cell = cellIterator.next(); // Storage the cell

					// It'll create a new cell in the unition Sheet and a new cell
					Cell cellOfunitionSheet = rowOfunitionSheet.createCell(cellNum++);

					// Cell's data is retrieved based on its type.
					switch (cell.getCellType()) {
					case NUMERIC:
						cellOfunitionSheet.setCellValue(cell.getNumericCellValue());
						break;
					case STRING:
						cellOfunitionSheet.setCellValue(cell.getStringCellValue());
						break;
					default:
						break;
					}
				}
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream("C:\\Users\\KaiiaK\\Desktop\\Sheets Manager\\Done.xlsx");
			unitionWorkbook.write(fos);
		} catch (IOException e) {
			e.getMessage();
		}

		// Save the new sheets into a .zip file
		ArrayList<Workbook> workbookArray = new ArrayList<>();
		workbookArray.add(unitionWorkbook);
		Workbook[] workArray = workbookArray.toArray(new Workbook[0]);
		ZipSheet.sheetZipping("GridLab_SheetUnision", 1, workArray, unitionWorkbook);

		FileEntity fileEntity = DownloadZipService
				.zipToEntity(new File(System.getProperty("java.io.tmpdir") + "GridLab_SheetUnision" + ".zip"));

		unitionWorkbook.close();
		return fileEntity;

	}

}