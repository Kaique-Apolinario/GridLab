package sheetmanager.application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) throws IOException {

		// Create Workbook instance holding reference to .xlsx file
		FileInputStream file = new FileInputStream(
				"C:\\Users\\KaiiaK\\Desktop\\Sheets Manager\\file_example_XLSX_100.xlsx");

		// How many sheets do you want the old sheet to be divided in?
		int amountOfNewSheets = 5;
		boolean header = true;
		// Get first/desired sheet from the workbook
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		System.out.println(file);

		// For each row in the sheet, it'll create a iterator of cells
		int amountOfRowsInOriginalSheet = sheet.getPhysicalNumberOfRows();
		int amountOfRowsInNewSheets = amountOfRowsInOriginalSheet / amountOfNewSheets;

		// Here, it is create a lot of new workbooks with new sheets
		XSSFWorkbook[] listOfNewWorkbook = new XSSFWorkbook[amountOfNewSheets];
		XSSFSheet[] listOfNewSheets = new XSSFSheet[amountOfNewSheets];
        for (int i = 0; i < amountOfNewSheets; i++) {
            listOfNewWorkbook[i] = new XSSFWorkbook();
            listOfNewSheets[i] = listOfNewWorkbook[i].createSheet("Sheet1");
        }

		int rowNumber = 0;
		int selectedSheet = 0;

		for (Row row : sheet) {
			
			if (rowNumber == amountOfRowsInNewSheets && selectedSheet < amountOfNewSheets - 1) {
				selectedSheet++;
				rowNumber = 0;
			}

//Cell's data is retrieved based on its type.
			Row rowInNewSheet = listOfNewSheets[selectedSheet].createRow(rowNumber++);
			int cellnum = 0;

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				Cell cellOfNewSheet = rowInNewSheet.createCell(cellnum++);

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
                    "C:\\Users\\KaiiaK\\Desktop\\Sheets Manager\\new_sheet_" + (i+1) + ".xlsx")) {
                listOfNewWorkbook[i].write(out);
            }
            listOfNewWorkbook[i].close();
        }
        
        // Close resources
        workbook.close();
        file.close();
        
        System.out.println("Done!");
    }
}