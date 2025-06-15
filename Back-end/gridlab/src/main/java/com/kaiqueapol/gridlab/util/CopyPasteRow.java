package com.kaiqueapol.gridlab.util;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

@Component
public class CopyPasteRow {

	public void copyPasteRow(Row originalRow, Sheet selectedSheet, int newSheetRow) {
		// It creates a new row in the current sheet
		Row newRow = selectedSheet.createRow(newSheetRow);
		int cellnum = 0;

		// For each row in the sheet, it'll create a cell iterator
		Iterator<Cell> cellIterator = originalRow.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			Cell cellOfNewSheet = newRow.createCell(cellnum++);

			// Cell's data is retrieved based on its type.
			switch (cell.getCellType()) {
			case NUMERIC:
				cellOfNewSheet.setCellValue(cell.getNumericCellValue());
				break;
			case STRING:
				cellOfNewSheet.setCellValue(cell.getStringCellValue());
				break;
			case BOOLEAN:
				cellOfNewSheet.setCellValue(cell.getBooleanCellValue());
				break;
			case FORMULA:
				cellOfNewSheet.setCellFormula(cell.getCellFormula());
				break;
			case BLANK:
				cellOfNewSheet.setBlank();
				break;
			case ERROR:
				cellOfNewSheet.setCellValue("ERROR");
				break;
			default:
				cellOfNewSheet.setCellValue("UNKNOWN");
				break;
			}
			// System.out.print(cellOfNewSheet + " ");

		}
		// System.out.println("\n");

	}
}
