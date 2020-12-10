package com.osadchuk.atm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCard {
	@NotBlank(message = "cardNumber is blank")
	String cardNumber;

	@NotBlank(message = "accountNumber is blank")
	String accountNumber;

	@NotBlank(message = "pin is blank")
	String pin;

	int money;
}
