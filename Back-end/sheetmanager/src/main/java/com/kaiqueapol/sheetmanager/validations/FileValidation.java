package com.kaiqueapol.sheetmanager.validations;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidation {

	public MultipartFile sheetValidation(MultipartFile rawFile) throws Exception {
		if (!(rawFile.getOriginalFilename().endsWith(".xlsx") || (rawFile.getOriginalFilename().endsWith(".xls")))) {
			throw new Exception();
		}
		return rawFile;
	}
}
