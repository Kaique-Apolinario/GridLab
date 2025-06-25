package com.kaiqueapol.gridlab.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "T_RC_FILEENTITY")
@ToString(exclude = "user")
public class FileEntity {

	@Id
	@Column(name = "idFile")
	private UUID id;

	@Column(name = "fileName")
	private String fileName;

	@Column(name = "fileUrl")
	private String dlUrl;

	@Column(name = "size")
	private Long size;

	@Column(name = "contentType")
	private String contentType;

	@Column(name = "sheetData")
	@Lob
	private byte[] data;

	@Column(name = "timeNDate")
	private LocalDateTime timeNDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public FileEntity(UUID id, String fileName, Long size, String contentType, byte[] data, LocalDateTime timeNDate,
			UserEntity user) {
		this.id = id;
		this.fileName = fileName;
		this.dlUrl = "/download/" + id;
		this.size = size;
		this.contentType = contentType;
		this.data = data;
		this.timeNDate = timeNDate;
		this.user = user;
	}

}
