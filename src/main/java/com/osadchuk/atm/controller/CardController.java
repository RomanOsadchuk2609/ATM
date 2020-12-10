package com.osadchuk.atm.controller;

import com.osadchuk.atm.model.Card;
import com.osadchuk.atm.model.NewCard;
import com.osadchuk.atm.model.Session;
import com.osadchuk.atm.service.CardService;
import com.osadchuk.atm.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

	private final CardService cardService;

	private final SessionService sessionService;

	@GetMapping("/get-all")
	public ResponseEntity<List<Card>> getAll() {
		return ResponseEntity.ok(cardService.findAll());
	}

	@PostMapping
	public ResponseEntity<Card> create(@RequestBody @Valid NewCard newCard) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(cardService.createNewCard(newCard));
	}

	@PostMapping("/{cardNumber}/validate/cvv/{cvvHash}/pvv/{pvvHash}")
	public ResponseEntity<Session> validate(@PathVariable String cardNumber,
	                                        @PathVariable String cvvHash,
	                                        @PathVariable String pvvHash) {
		return ResponseEntity.ok(cardService.createSession(cardNumber, cvvHash, pvvHash));
	}

	@GetMapping("/session/{sessionId}/get-balance")
	public ResponseEntity<Integer> getBalance(@PathVariable UUID sessionId) {
		return ResponseEntity.ok(cardService.getBalance(sessionId));
	}

	@GetMapping("/session/{sessionId}/withdraw-money/{amount}")
	public ResponseEntity<Integer> withdrawMoney(@PathVariable UUID sessionId, @PathVariable int amount) {
		return ResponseEntity.ok(cardService.withdrawMoney(sessionId, amount));
	}

	@PutMapping("/session/{sessionId}/put-money/{amount}")
	public ResponseEntity<Integer> putMoney(@PathVariable UUID sessionId, @PathVariable int amount) {
		return ResponseEntity.ok(cardService.putMoney(sessionId, amount));
	}

	@PutMapping("/session/{sessionId}/close")
	public ResponseEntity<Session> closeSession(@PathVariable UUID sessionId) {
		return ResponseEntity.ok(sessionService.close(sessionId));
	}


}
