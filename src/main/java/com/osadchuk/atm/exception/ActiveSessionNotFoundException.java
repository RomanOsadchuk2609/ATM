package com.osadchuk.atm.exception;

import java.util.UUID;

public class ActiveSessionNotFoundException extends RuntimeException {

	public ActiveSessionNotFoundException(UUID sessionId) {
		super(String.format("Active session with id %s not found", sessionId));
	}
}
