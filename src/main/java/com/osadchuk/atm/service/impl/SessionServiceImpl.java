package com.osadchuk.atm.service.impl;

import com.osadchuk.atm.exception.ActiveSessionNotFoundException;
import com.osadchuk.atm.exception.SessionNotFoundException;
import com.osadchuk.atm.model.Session;
import com.osadchuk.atm.nitrite.NitriteConnectionProvider;
import com.osadchuk.atm.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

	private final NitriteConnectionProvider nitriteConnectionProvider;

	private ObjectRepository<Session> sessionRepository;

	@PostConstruct
	void init() {
		sessionRepository = nitriteConnectionProvider.getDataBase().getRepository(Session.class);
	}

	@Override
	public Session create(String cardNumber) {
		Session session = Session.builder()
				.id(UUID.randomUUID())
				.cardNumber(cardNumber)
				.isActive(true)
				.build();
		sessionRepository.insert(session);
		return session;
	}

	@Override
	public List<Session> findAll() {
		return Optional.ofNullable(sessionRepository.find().toList())
				.orElseGet(Collections::emptyList);
	}

	@Override
	public Session findById(UUID id) {
		return Optional.ofNullable(sessionRepository.find(eq("id", id)).firstOrDefault())
				.orElseThrow(() -> new SessionNotFoundException(id));
	}

	@Override
	public Session findActiveById(UUID id) {
		return Optional.ofNullable(sessionRepository.find(eq("id", id)).firstOrDefault())
				.filter(Session::isActive)
				.orElseThrow(() -> new ActiveSessionNotFoundException(id));
	}

	@Override
	public boolean isActive(UUID id) {
		return findById(id).isActive() ;
	}

	@Override
	public Session close(UUID id) {
		Session session = findById(id);
		session.setActive(false);
		sessionRepository.update(session);
		return session;
	}
}
