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

		// Path of the specific directory
		String directoryPath = "C:\\Users\\KaiiaK\\Desktop\\Sheets Manager";

		// Using File class create an object for specific directory
		File directory = new File(directoryPath);

		// Using listFiles method we get all the files of a directory
		// return type of listFiles is array
		File[] validatedFiles = directory.listFiles();

		// Print name of the all files present in that path
		if (validatedFiles != null) {
			for (File file : validatedFiles) {
				System.out.println(file.getName());
			}
		}
	}

	File[] validatedFiles = null;
	XSSFWorkbook unisionWorkbook = new XSSFWorkbook();unisionWorkbook.createSheet("Unision sheet");

	List<XSSFWorkbook> ogFilesWorkbooks = null;for(
	File file:listOfSheets)
	{
		XSSFWorkbook fileWorkbook = new XSSFWorkbook(file);
		ogFilesWorkbooks.add(fileWorkbook);
	}

	for(
	XSSFWorkbook originalSheet:ogFilesWorkbooks)
	{
		System.out.println(originalSheet);
	}
}