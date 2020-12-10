package com.osadchuk.atm.service;

import com.osadchuk.atm.model.Card;
import com.osadchuk.atm.model.NewCard;
import com.osadchuk.atm.model.Session;

import java.util.List;
import java.util.UUID;

public interface CardService {

	public Card createNewCard(NewCard newCard);

	public List<Card> findAll();

	public Card findByCardNumber(String cardNumber);

	public void validateCard(Card card, String cvvHash);

	public void validateUser(Card card, String pvvHash);

	public Session createSession(String cardNumber, String cvvHash, String pvvHash);

	public Card findSessionId(UUID sessionId);

	public Integer getBalance(UUID sessionId);

	public Integer withdrawMoney(UUID sessionId, Integer money);

	public Integer putMoney(UUID sessionId, Integer money);
}
