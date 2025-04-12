package com.kaiqueapol.sheetmanager.validations;

import org.springframework.stereotype.Component;

import com.kaiqueapol.sheetmanager.exceptions.TooManySheetsException;

@Component
public class RowPerSheetsValidation {

	public void rowPerSheetsValidation(int amountOfRowsInNewSheets, int sheetParts) throws TooManySheetsException {

		if (amountOfRowsInNewSheets < sheetParts) {
			throw new TooManySheetsException();
		}

	}

}
