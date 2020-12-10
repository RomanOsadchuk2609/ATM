package com.osadchuk.atm.exception;

public class InvalidPvvHashException extends RuntimeException {

	public InvalidPvvHashException(String cardNumber, String pvvHash) {
		super(String.format("Invalid pvv hash %s for card %s", pvvHash, cardNumber));
	}
}
