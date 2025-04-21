package com.kaiqueapol.gridlab.util;

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
import org.springframework.stereotype.Component;

@Component
public class ZipSheet {

	public void sheetZipping(String ogName, int amountOfNewSheets, Workbook[] listOfNewWorkbook, Workbook workbook)
			throws FileNotFoundException, IOException {
		System.out.println(listOfNewWorkbook[0].getSheetAt(0).getRow(2).getCell(0).toString());

		// It creates a list with every .xlsx created
		List<String> listWithEveryNewSheet = new ArrayList<>();
		for (int i = 0; i < amountOfNewSheets; i++) {
			String fileName = "UploadFolder\\" + ogName + "_" + (i + 1) + ".xlsx";
			listWithEveryNewSheet.add(fileName);

			// It takes each sheet from listOfNewWorkbook and write a .xlsx based on its
			// content and on the fileName's string
			try {
				FileOutputStream fos = new FileOutputStream(fileName);
				listOfNewWorkbook[i].write(fos);
				listOfNewWorkbook[i].close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		// It creates a .zip including all of the .xlsx within listWithEveryNewSheet
		try (FileOutputStream fos = new FileOutputStream("UploadFolder\\" + ogName + ".zip");
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
					zos.closeEntry();
				} catch (IOException e) {
					e.getMessage();
				}
				fileToZip.delete();
			}
		} catch (IOException e) {
			e.getMessage();
		}
		workbook.close();

	}
}