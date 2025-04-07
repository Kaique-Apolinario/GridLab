package com.kaiqueapol.sheetmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.sheetmanager.entities.FileEntity;
import com.kaiqueapol.sheetmanager.services.SheetService;

@RestController
public class SheetController {
	private SheetService sheetService;

	public SheetController(SheetService sheetService) {
		this.sheetService = sheetService;
	}

	@PostMapping("/upload")
	public ResponseEntity<FileEntity> uploadSheet(@RequestPart("file") MultipartFile file, int sheetParts,
			boolean header, boolean saveFile) throws Exception {
		sheetService.divideSheets(file, sheetParts, header, saveFile);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/downloadFile/{fileCode}")
	public ResponseEntity<FileEntity> downloadFile(@PathVariable String fileCode) {
		return ResponseEntity.ok(sheetService.downloadZip(fileCode));
	}

}
