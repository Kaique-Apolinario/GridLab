package com.kaiqueapol.gridlab.controllers;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.dto.fileEntityDTO;
import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.services.SheetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(produces = { "application/json" })
@Tag(name = "GridLabAPI")
public class SheetController {
	private SheetService sheetService;

	public SheetController(SheetService sheetService) {
		this.sheetService = sheetService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload")
	@Operation(summary = "It uploads the file into the API", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucess"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<fileEntityDTO> uploadSheet(@RequestPart("file") MultipartFile file,
			@RequestParam("sheetParts") int sheetParts, @RequestParam("header") boolean header) throws Exception {

		System.out.println(file.toString());
		System.out.println(sheetParts);
		System.out.println(header);
		System.out.println(file.getContentType());

		FileEntity fileEntity = sheetService.divideSheets(file, sheetParts, header);

		return ResponseEntity.ok().body(new fileEntityDTO(fileEntity.getFileName(), fileEntity.getContentType(),
				fileEntity.getSize(), fileEntity.getDlUrl()));
	}

	@GetMapping("/download/{id}")
	@Operation(summary = "It downloads the file from the URL parameter", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucess"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) {

		Resource resource = sheetService.downloadZip(id);

		FileEntity fileEntity = sheetService.getEntityById(id);
		String headerValue = "attachment; filename=\"" + fileEntity.getFileName() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
	}

}
