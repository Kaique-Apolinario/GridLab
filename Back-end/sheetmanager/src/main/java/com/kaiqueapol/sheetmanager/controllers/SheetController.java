package com.kaiqueapol.sheetmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.sheetmanager.services.SheetService;

@RestController
public class SheetController {
	private SheetService sheetService;

	public SheetController(SheetService sheetService) {
		this.sheetService = sheetService;
	}

	public ResponseEntity<String> uploadSheet(@RequestPart("file") MultipartFile file, int sheetParts, boolean header)
			throws Exception {
		sheetService.divideSheets(file, sheetParts, header);
		return ResponseEntity.ok("File updated sucessfully!");
	}
}
