package com.kaiqueapol.sheetmanager.controllers;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.sheetmanager.dto.fileEntityDTO;
import com.kaiqueapol.sheetmanager.entities.FileEntity;
import com.kaiqueapol.sheetmanager.services.SheetService;

@RestController
public class SheetController {
	private SheetService sheetService;

	public SheetController(SheetService sheetService) {
		this.sheetService = sheetService;
	}

	@PostMapping("/upload")
	public ResponseEntity<fileEntityDTO> uploadSheet(@RequestPart("file") MultipartFile file, int sheetParts,
			boolean header, boolean saveFile) throws Exception {
		FileEntity fileEntity = sheetService.divideSheets(file, sheetParts, header, saveFile);
		return ResponseEntity.ok().body(new fileEntityDTO(fileEntity.getFileName(), fileEntity.getContentType(),
				fileEntity.getSize(), fileEntity.getDlUrl()));
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) {

		Resource resource = sheetService.downloadZip(id);

		FileEntity fileEntity = sheetService.getEntityById(id);
		String headerValue = "attachment; filename=\"" + fileEntity.getFileName() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
	}

}
