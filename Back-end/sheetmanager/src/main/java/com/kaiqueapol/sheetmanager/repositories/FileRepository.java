package com.kaiqueapol.sheetmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaiqueapol.sheetmanager.entities.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

	public FileEntity getFileEntityByFileCode(String fileCode);
}
