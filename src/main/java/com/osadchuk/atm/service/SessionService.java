package com.osadchuk.atm.service;

import com.osadchuk.atm.model.Session;

import java.util.List;
import java.util.UUID;

public interface SessionService {

	Session create(String cardNumber);

	List<Session> findAll();

	Session findById(UUID id);

	Session findActiveById(UUID id);

	boolean isActive(UUID id);

	Session close(UUID id);
}
