package com.kaiqueapol.gridlab.services;

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
	private FileRepository fileRep;

	public DownloadZipService(FileRepository fileRep) {
		this.fileRep = fileRep;
	}

	public Resource downloadZip(UUID id) {
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
}
