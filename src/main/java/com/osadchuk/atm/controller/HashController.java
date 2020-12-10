package com.osadchuk.atm.controller;

import com.osadchuk.atm.service.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hash")
public class HashController {

	private final HashService hashService;

	@Autowired
	public HashController(HashService hashService) {
		this.hashService = hashService;
	}

	@PostMapping("/triple")
	public ResponseEntity<String> hash(@RequestBody String value) {
		return ResponseEntity.ok(hashService.tripleHash(value));
	}
}
