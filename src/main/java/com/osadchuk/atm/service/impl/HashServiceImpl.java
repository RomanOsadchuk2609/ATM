package com.osadchuk.atm.service.impl;

import com.osadchuk.atm.service.HashService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class HashServiceImpl implements HashService {

	@Override
	public String hash(String value) {
		return DigestUtils.sha256Hex(value);
	}
}
