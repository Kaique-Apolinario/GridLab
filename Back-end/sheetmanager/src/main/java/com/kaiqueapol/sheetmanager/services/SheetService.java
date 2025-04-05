package com.kaiqueapol.sheetmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SheetService {

	public void divideSheets(MultipartFile file) throws Exception {
		validateSheet(file);
	}

	public void validateSheet(MultipartFile file) throws Exception {
		if (file.getName().endsWith(".xlsx") || (file.getName().endsWith(".xls"))) {
			throw new Exception();
		}
	}
}
