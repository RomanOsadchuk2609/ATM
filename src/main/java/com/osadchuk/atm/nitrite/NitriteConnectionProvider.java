package com.osadchuk.atm.nitrite;

import com.osadchuk.atm.model.Card;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NitriteConnectionProvider {

	private Nitrite dataBase;

	@PostConstruct
	void init() {
		dataBase = Nitrite.builder()
				.filePath("atm.db")
				.openOrCreate();
	}

	@PreDestroy
	void closeConnection() {
		dataBase.close();
	}

	public Nitrite getDataBase() {
		return dataBase;
	}
}
