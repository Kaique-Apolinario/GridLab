package com.kaiqueapol.gridlab.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.entities.dto.FileEntityDto;
import com.kaiqueapol.gridlab.services.DownloadZipService;
import com.kaiqueapol.gridlab.services.SheetDividerService;
import com.kaiqueapol.gridlab.services.SheetMergerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(produces = { "application/json" })
@AllArgsConstructor
@Tag(name = "GridLabAPI file methods")
public class SheetController {
	private final SheetDividerService sheetDividerService;
	private final SheetMergerService sheetMergerService;
	private final DownloadZipService downZipServ;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload/divider/")
	@Operation(summary = "It uploads a file to be divided into the API", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<FileEntityDto> uploadSheetToDivide(@RequestPart("file") MultipartFile file,
			@RequestParam("sheetParts") int sheetParts, @RequestParam("header") boolean header) throws Exception {
System.out.println("  COME AND GET YEAH COME AND GET YEAH");
		FileEntity fileEntity = sheetDividerService.divideSheets(file, sheetParts, header);

		return ResponseEntity.ok().body(new FileEntityDto(fileEntity.getFileName(), fileEntity.getContentType(),
				fileEntity.getSize(), fileEntity.getDlUrl()));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/upload/merger/")
	@Operation(summary = "It uploads files to be merged into the API", method = "POST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<FileEntityDto> uploadSheetToMerge(@RequestPart("files") List<MultipartFile> file,
			@RequestParam("ignoreRepeatedRows") boolean ignoreRepeatedRows) throws Exception {
		FileEntity fileEntity = sheetMergerService.mergeSheets(file, ignoreRepeatedRows);

		return ResponseEntity.ok().body(new FileEntityDto(fileEntity.getFileName(), fileEntity.getContentType(),
				fileEntity.getSize(), fileEntity.getDlUrl()));
	}

	@GetMapping("/download/{id}")
	@Operation(summary = "It downloads the file from the URL parameter", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) throws IOException {
		Resource resource = downZipServ.downloadZip(id);

		FileEntity fileEntity = downZipServ.getEntityById(id);
		String headerValue = "attachment; filename=\"" + fileEntity.getFileName() + "\"";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
	}

	/**
	 * @GetMapping("/fileLib")
	 * 
	 * @Operation(summary = "It returns every uploaded file from the database",
	 *                    method = "GET")
	 * @ApiResponses(value = { @ApiResponse(responseCode = "200", description =
	 *                     "Sucess"),
	 * @ApiResponse(responseCode = "422", description = "Invalid data requisition"),
	 * @ApiResponse(responseCode = "400", description = "Invalid parameters"),
	 * @ApiResponse(responseCode = "500", description = "Internal server error"), })
	 *                           public ResponseEntity<List<FileEntity>>
	 *                           loadAllFiles() {
	 * 
	 *                           List<FileEntity> fileEntityList =
	 *                           downZipServ.getAllFiles();
	 * 
	 *                           return ResponseEntity.ok().body(fileEntityList); }
	 */

	@GetMapping("/fileLib/{userId}")
	@Operation(summary = "It returns every uploadedfile of a specific user from the database", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "422", description = "Invalid data requisition"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error"), })
	public ResponseEntity<List<FileEntity>> loadAllFilesFromUser(@RequestHeader("Authorization") String userToken,
			@PathVariable Long userId) {

		List<FileEntity> fileEntityList = downZipServ.getAllFilesFromUser(userToken, userId);
		return ResponseEntity.ok().body(fileEntityList);
	}

}
