package com.kaiqueapol.gridlab.entities.dto;

public record FileEntityDto(String fileName, String contentType, Long size, String dlUrl) {
}
