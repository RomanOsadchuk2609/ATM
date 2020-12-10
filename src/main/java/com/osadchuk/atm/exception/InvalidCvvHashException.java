package com.osadchuk.atm.exception;

public class InvalidCvvHashException extends RuntimeException {

	public InvalidCvvHashException(String cardNumber, String cvvHash) {
		super(String.format("Invalid cvv hash %s for card %s", cvvHash, cardNumber));
	}
}
