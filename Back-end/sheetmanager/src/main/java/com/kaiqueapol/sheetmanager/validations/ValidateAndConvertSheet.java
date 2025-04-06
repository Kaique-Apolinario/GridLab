package com.kaiqueapol.sheetmanager.validations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ValidateAndConvertSheet {

	public File sheetValidationAndConvertion(MultipartFile rawFile) throws Exception {
		if (rawFile.getName().endsWith(".xlsx") || (rawFile.getName().endsWith(".xls"))) {
			throw new Exception();
		} else {
			File validFile = new File(rawFile.getOriginalFilename());
			try {
				validFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(validFile);
				fos.write(rawFile.getBytes());
				fos.close();
			} catch (IOException e) {
				throw new IOException();
			}
			return validFile;
		}
	}
}
