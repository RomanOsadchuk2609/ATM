package com.osadchuk.atm.exception;

public class CardNotFoundException extends RuntimeException {

	public CardNotFoundException(String cardNumber) {
		super(String.format("Card with cardNumber %s not found", cardNumber));
	}
}
