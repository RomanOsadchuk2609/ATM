package com.osadchuk.atm.service.impl;

import com.osadchuk.atm.exception.CardNotFoundException;
import com.osadchuk.atm.exception.InvalidCvvHashException;
import com.osadchuk.atm.exception.InvalidPvvHashException;
import com.osadchuk.atm.exception.NotEnoughMoneyException;
import com.osadchuk.atm.model.Card;
import com.osadchuk.atm.model.NewCard;
import com.osadchuk.atm.model.Session;
import com.osadchuk.atm.nitrite.NitriteConnectionProvider;
import com.osadchuk.atm.service.CardService;
import com.osadchuk.atm.service.HashService;
import com.osadchuk.atm.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

	private final HashService hashService;

	private final SessionService sessionService;

	private final NitriteConnectionProvider nitriteConnectionProvider;

	private ObjectRepository<Card> cardRepository;

	@PostConstruct
	void init() {
		cardRepository = nitriteConnectionProvider.getDataBase().getRepository(Card.class);
		if (findAll().isEmpty()) {
			List<NewCard> newCards = Arrays.asList(
					new NewCard("1111222233334444", "111122223333444400", "1111", 1000),
					new NewCard("5555666677778888", "555566667777888800", "2222", 2000),
					new NewCard("9999000011112222", "999900001111222200", "3333", 3000)
			);
			newCards.forEach(this::createNewCard);
		}
	}

	@Override
	public Card createNewCard(NewCard newCard) {
		String pvvHash = getFirstHalf(hashService.tripleHash(newCard.getPin()));
		String cvvHash = getFirstHalf(hashService.tripleHash(newCard.getCardNumber() + newCard.getAccountNumber()));
		Card card = Card.builder()
				.cardNumber(newCard.getCardNumber())
				.accountNumber(newCard.getAccountNumber())
				.cvvHash(cvvHash)
				.pvvHash(pvvHash)
				.money(newCard.getMoney())
				.build();
		cardRepository.insert(card);
		return card;
	}

	@Override
	public List<Card> findAll() {
		return Optional.ofNullable(cardRepository.find().toList())
				.orElseGet(Collections::emptyList);
	}

	@Override
	public Card findByCardNumber(String cardNumber) {
		return Optional.ofNullable(cardRepository.find(eq("cardNumber", cardNumber)).firstOrDefault())
				.orElseThrow(() -> new CardNotFoundException(cardNumber));
	}

	@Override
	public void validateCard(Card card, String cvvHash) {
		Optional.of(card)
				.map(Card::getCvvHash)
				.filter(cvvHash::equals)
				.orElseThrow(() -> new InvalidCvvHashException(card.getCardNumber(), cvvHash));
	}

	@Override
	public void validateUser(Card card, String pvvHash) {
		Optional.of(card)
				.map(Card::getPvvHash)
				.filter(pvvHash::equals)
				.orElseThrow(() -> new InvalidPvvHashException(card.getCardNumber(), pvvHash));
	}

	@Override
	public Session createSession(String cardNumber, String cvvHash, String pvvHash) {
		Card card = findByCardNumber(cardNumber);
		validateCard(card, cvvHash);
		validateUser(card, pvvHash);
		return sessionService.create(cardNumber);
	}

	@Override
	public Card findSessionId(UUID sessionId) {
		return findByCardNumber(sessionService.findActiveById(sessionId).getCardNumber());
	}

	@Override
	public Integer getBalance(UUID sessionId) {
		return findSessionId(sessionId).getMoney();
	}

	@Override
	public Integer withdrawMoney(UUID sessionId, Integer money) {
		Card card = findSessionId(sessionId);
		if (card.getMoney() >= money) {
			card.setMoney(card.getMoney() - money);
			cardRepository.update(card);
			return card.getMoney();
		} else {
			throw new NotEnoughMoneyException(card.getCardNumber());
		}
	}

	@Override
	public Integer putMoney(UUID sessionId, Integer money) {
		Card card = findSessionId(sessionId);
		card.setMoney(card.getMoney() + money);
		cardRepository.update(card);
		return card.getMoney();
	}

	private String getFirstHalf(String originalString) {
		return originalString.substring(0, originalString.length() / 2);
	}
}
