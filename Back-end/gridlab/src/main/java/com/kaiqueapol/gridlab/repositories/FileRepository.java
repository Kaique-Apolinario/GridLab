package com.kaiqueapol.gridlab.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kaiqueapol.gridlab.entities.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

	public FileEntity getFileEntityById(UUID id);

	@Query("SELECT file FROM FileEntity file WHERE file.user.id = :id")
	public List<FileEntity> fileListByUser(int id);

}
