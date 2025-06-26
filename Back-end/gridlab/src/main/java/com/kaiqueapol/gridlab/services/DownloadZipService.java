package com.kaiqueapol.gridlab.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kaiqueapol.gridlab.entities.FileEntity;
import com.kaiqueapol.gridlab.entities.UserEntity;
import com.kaiqueapol.gridlab.infra.exceptions.FileEntityNotFoundException;
import com.kaiqueapol.gridlab.infra.exceptions.NosyException;
import com.kaiqueapol.gridlab.infra.exceptions.UserNotFoundException;
import com.kaiqueapol.gridlab.repositories.FileRepository;
import com.kaiqueapol.gridlab.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DownloadZipService {
	private FileRepository fileRep;
	private UserRepository userRepo;
	private TokenService tokenServ;

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
		Optional<List<FileEntity>> foundFilesList = Optional.ofNullable(fileRep.findAll());
		if (foundFilesList.isEmpty())
			throw new FileEntityNotFoundException();
		return foundFilesList.get();
	}

	public List<FileEntity> getAllFilesFromUser(String userToken, Long userId) {

		Optional<UserEntity> userFromUrlId = Optional.ofNullable(userRepo.findById(userId))
				.orElseThrow(() -> new UserNotFoundException());

		if (!userFromUrlId.isPresent())
			throw new UserNotFoundException("No user with the URL's Id");

		String userFromToken = tokenServ.returnUserFromToken(userToken.replace("Bearer ", ""));
		if (userFromUrlId.get().getEmail().equals(userFromToken)) {

			List<FileEntity> foundFilesList = Optional.ofNullable(fileRep.fileListByUser(userId))
					.orElseThrow(FileEntityNotFoundException::new);

			return foundFilesList;
		} else {
			throw new NosyException();
		}
	}

	@Transactional
	public FileEntity zipToEntity(File zip) throws IOException {

		// You need to get the user tracked/controlled by Hibernate, so you take from
		// the repo
		Optional<UserEntity> user = userRepo.findByEmailIgnoreCase(
				((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());

		// Attach current authenticated user to the file
		FileEntity fileEntity = new FileEntity(UUID.randomUUID(), zip.getName(), zip.length(),
				Files.probeContentType(zip.toPath()), Files.readAllBytes(zip.toPath()), LocalDateTime.now(),
				user.get());

		fileRep.save(fileEntity);

		// Attach current file to the user's list of files
		user.get().getFileList().add(fileEntity);
		userRepo.save(user.get());
		return fileEntity;
	}
}
