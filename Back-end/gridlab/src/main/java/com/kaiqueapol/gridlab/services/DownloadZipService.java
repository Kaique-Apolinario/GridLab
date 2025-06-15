package com.kaiqueapol.gridlab.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.exceptions.FileEntityNotFoundException;
import com.kaiqueapol.gridlab.repositories.FileRepository;

@Service
public class DownloadZipService {
	private static FileRepository fileRep;

	public DownloadZipService(FileRepository fileRep) {
		DownloadZipService.fileRep = fileRep;
	}

	public Resource downloadZip(UUID id) throws IOException {
		ByteArrayResource resource = new ByteArrayResource(getEntityById(id).getData());
		return resource;

	}

	public FileEntity getEntityById(UUID id) {
		FileEntity foundFile = Optional.ofNullable(fileRep.getFileEntityById(id))
				.orElseThrow(FileEntityNotFoundException::new);

		return foundFile;
	}

	public List<FileEntity> getAllFiles() {
		List<FileEntity> foundFilesList = Optional.ofNullable(fileRep.findAll())
				.orElseThrow(FileEntityNotFoundException::new);

		return foundFilesList;
	}

	public static FileEntity zipToEntity(File zip) throws IOException {
		FileEntity fileEntity = new FileEntity(UUID.randomUUID(), zip.getName(), zip.length(),
				Files.probeContentType(zip.toPath()), Files.readAllBytes(zip.toPath()));
		fileRep.save(fileEntity);
		return fileEntity;
	}
}
