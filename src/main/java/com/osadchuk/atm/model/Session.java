package com.osadchuk.atm.model;

import lombok.Builder;
import lombok.Data;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.util.UUID;

@Data
@Builder
@Indices({
		@Index(value = "id", type = IndexType.Unique)
})
public class Session {
	@Id
	private UUID id;
	private String cardNumber;
	private boolean isActive;
}
