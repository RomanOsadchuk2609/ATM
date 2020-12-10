package com.osadchuk.atm.exception;

import java.util.UUID;

public class SessionNotFoundException extends RuntimeException {

	public SessionNotFoundException(UUID sessionId) {
		super(String.format("Session with id %s not found", sessionId));
	}
}
