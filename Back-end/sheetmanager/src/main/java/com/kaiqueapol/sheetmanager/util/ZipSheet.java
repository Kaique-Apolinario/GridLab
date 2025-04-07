package com.kaiqueapol.sheetmanager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ZipSheet {

	public void sheetZipping(String ogName, int amountOfNewSheets, Workbook[] listOfNewWorkbook, XSSFWorkbook workbook)
			throws FileNotFoundException, IOException {

		// It creates a list with every .xlsx created
		List<String> listWithEveryNewSheet = new ArrayList<>();
		for (int i = 0; i < amountOfNewSheets; i++) {
			String fileName = ogName + "_" + (i + 1) + ".xlsx";
			listWithEveryNewSheet.add(fileName);

			// It takes each sheet from listOfNewWorkbook and write a .xlsx based on its
			// content and on the fileName's string
			try {
				FileOutputStream fos = new FileOutputStream(fileName);
				listOfNewWorkbook[i].write(fos);
				listOfNewWorkbook[i].close();

			} catch (Exception e) {

			}
		}

		// It creates a .zip including all of the .xlsx within listWithEveryNewSheet
		try (FileOutputStream fos = new FileOutputStream("UploadFolder\\SheetZip.zip");
				ZipOutputStream zos = new ZipOutputStream(fos);) {
			for (String part : listWithEveryNewSheet) {
				File fileToZip = new File(part);

				// Inserts each .xlsx into the .zip file
				try (FileInputStream fis = new FileInputStream(fileToZip)) {
					ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
					zos.putNextEntry(zipEntry);

					byte[] bytes = new byte[1024];
					int length;
					while ((length = fis.read(bytes)) >= 0) {
						zos.write(bytes, 0, length);
					}
					fileToZip.delete();
				}
			}
		}
		workbook.close();

	}
}