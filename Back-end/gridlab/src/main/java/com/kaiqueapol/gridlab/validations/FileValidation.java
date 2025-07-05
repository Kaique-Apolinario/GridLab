package com.kaiqueapol.gridlab.validations;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.infra.exceptions.InvalidFileException;

@Component
public class FileValidation { // Stateless class

	public static MultipartFile sheetValidation(MultipartFile rawFile) throws InvalidFileException {
		if (!(rawFile.getOriginalFilename().endsWith(".xlsx") || (rawFile.getOriginalFilename().endsWith(".xls")))) {
			throw new InvalidFileException();
		}
		return rawFile;
	}
}
