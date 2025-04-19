package com.kaiqueapol.gridlab.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaiqueapol.gridlab.entities.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

	public FileEntity getFileEntityById(UUID id);

}
