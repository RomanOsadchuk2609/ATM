package com.osadchuk.atm.exception;

public class NotEnoughMoneyException extends RuntimeException {

	public NotEnoughMoneyException(String cardNumber) {
		super("Not enough money on card " + cardNumber);
	}
}
