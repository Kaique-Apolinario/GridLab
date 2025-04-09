package com.kaiqueapol.sheetmanager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaiqueapol.sheetmanager.entities.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

	public FileEntity getFileEntityById(UUID id);

}
