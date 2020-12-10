package com.osadchuk.atm.model;

import lombok.Builder;
import lombok.Data;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

@Data
@Builder
@Indices({
		@Index(value = "cardNumber", type = IndexType.Unique),
		@Index(value = "accountNumber", type = IndexType.Unique)
})
public class Card {
	@Id
	String cardNumber;
	String accountNumber;
	String cvvHash;
	String pvvHash;
	int money;
}
