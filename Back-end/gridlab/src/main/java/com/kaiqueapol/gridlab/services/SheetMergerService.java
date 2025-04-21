package com.kaiqueapol.gridlab.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.validations.FileValidation;

@Service
public class SheetMergerService {
	private FileValidation fileValidation;

	public void mergeSheets(List<MultipartFile> listOfSheets) throws IOException, InvalidFormatException {
		// return type of listFiles is array
		List<MultipartFile> validatedFiles = listOfSheets;

		// Print name of the all files present in that path
		if (validatedFiles != null) {
			System.out.println("DREAMLIKE");
			for (MultipartFile file : validatedFiles) {
				System.out.println("CATCH UP");
				System.out.println(file.getName());
			}
		}

		XSSFWorkbook unitionWorkbook = new XSSFWorkbook();
		unitionWorkbook.createSheet("unition sheet");

		List<XSSFWorkbook> ogFilesWorkbooks = new ArrayList();
		for (MultipartFile file : validatedFiles) {
			XSSFWorkbook fileWorkbook = new XSSFWorkbook(file.getInputStream());
			ogFilesWorkbooks.add(fileWorkbook);
		}

		int usRowNumber = 0;
		for (XSSFWorkbook originalSheet : ogFilesWorkbooks) { // For each original sheet

			for (Row row : originalSheet.getSheetAt(0)) { // I'll read its row

				Iterator<Cell> cellIterator = row.cellIterator(); // And will create a iterator of cells based on the
																	// original sheet row

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
					System.out.print(cellOfunitionSheet + "  ");
				}
				System.out.println("\n");
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream("C:\\Users\\KaiiaK\\Desktop\\Sheets Manager\\Done.xlsx");
			unitionWorkbook.write(fos);
			unitionWorkbook.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}

}