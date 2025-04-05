package com.kaiqueapol.sheetmanager.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.kaiqueapol.sheetmanager.services.SheetService;

@RestController
public class SheetController {
	private SheetService sheetService;

	public SheetController(SheetService sheetService) {
		this.sheetService = sheetService;
	}

}
