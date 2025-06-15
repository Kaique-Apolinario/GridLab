package com.kaiqueapol.gridlab.validations;

import org.springframework.stereotype.Component;

import com.kaiqueapol.gridlab.exceptions.TooManySheetsException;

@Component
public class RowPerSheetsValidation { // Stateless class

	public static void rowPerSheetsValidation(int amountOfRowsInOriginalSheet, int sheetParts)
			throws TooManySheetsException {

		if (amountOfRowsInOriginalSheet < sheetParts) {
			throw new TooManySheetsException();
		}

	}

}
