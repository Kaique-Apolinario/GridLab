package sheetmanager.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) throws IOException {

		// Create Workbook instance holding reference to .xlsx file
		File file = new File("C:\\Users\\KaiiaK\\Desktop\\Sheets Manager\\file_example_XLSX_100.xlsx");

		String excelVersion = null;

		System.out.println(file.getName());
		if (file.getName().endsWith(".xlsx")) {
			excelVersion = "New";
		} else if (file.getName().endsWith(".xls")) {
			excelVersion = "Old";
		}

		// How many sheets do you want the old sheet to be divided in?
		int amountOfNewSheets = 5;

		// Do you want the first line to be the same in every sheet?
		boolean header = true;

		// Get first/desired sheet from the workbook
		Sheet sheet = null;
		try {
				FileInputStream fis = new FileInputStream(file);
				
			if ("New".equals(excelVersion)) {
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				sheet = workbook.getSheetAt(0);

			} else if ("Old".equals(excelVersion)) {
				HSSFWorkbook workbook = new HSSFWorkbook(fis);
				sheet = workbook.getSheetAt(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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
				if (header) {
					// It creates a new row in the current sheet
					Row rowInNewSheet = listOfNewSheets[selectedSheet].createRow(rowNumber++);
					int cellnum = 0;

					// For each row in the sheet, it'll create a iterator of cells
					Iterator<Cell> cellIterator = sheet.getRow(sheet.getFirstRowNum()).cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						Cell cellOfNewSheet = rowInNewSheet.createCell(cellnum++);

						// Cell's data is retrieved based on its type.
						switch (cell.getCellType()) {
						case NUMERIC:
							cellOfNewSheet.setCellValue(cell.getNumericCellValue());
							break;
						case STRING:
							cellOfNewSheet.setCellValue(cell.getStringCellValue());
							break;
						default:
							break;
						}
						System.out.print(cellOfNewSheet + "  ");

					}
					System.out.println("\n");

				}
			}

			// It creates a new row in the current sheet
			Row rowInNewSheet = listOfNewSheets[selectedSheet].createRow(rowNumber++);
			int cellnum = 0;

			// For each row in the sheet, it'll create a iterator of cells
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				Cell cellOfNewSheet = rowInNewSheet.createCell(cellnum++);

				// Cell's data is retrieved based on its type.
				switch (cell.getCellType()) {
				case NUMERIC:
					cellOfNewSheet.setCellValue(cell.getNumericCellValue());
					break;
				case STRING:
					cellOfNewSheet.setCellValue(cell.getStringCellValue());
					break;
				default:
					break;
				}
				System.out.print(cellOfNewSheet + "  ");

			}
			System.out.println("\n");

		}
		// Save new workbooks
		for (int i = 0; i < amountOfNewSheets; i++) {
			try (FileOutputStream out = new FileOutputStream(
					"C:\\Users\\KaiiaK\\Desktop\\Sheets Manager\\Sheet_Number_" + (i + 1) + ".xlsx")) {
				listOfNewWorkbook[i].write(out);
			}
			listOfNewWorkbook[i].close();
		}

		System.out.println("Done!");
	}
}