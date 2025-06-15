package com.kaiqueapol.gridlab.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "T_RC_FILEENTITY")
public class FileEntity {

	@Id
	private UUID id;

	private String fileName;

	private String dlUrl;

	private Long size;

	private String contentType;

	@Lob
	private byte[] data;

	private LocalDateTime timeNDate;

	public FileEntity(UUID id, String fileName, Long size, String contentType, byte[] data, LocalDateTime timeNDate) {
		this.id = id;
		this.fileName = fileName;
		this.dlUrl = "/download/" + id;
		this.size = size;
		this.contentType = contentType;
		this.data = data;
		this.timeNDate = timeNDate;
	}

}
