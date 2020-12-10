package com.osadchuk.atm.service;

public interface HashService {

	String hash(String value);

	default String tripleHash(String value) {
		return hash(hash(hash(value)));
	}

}
