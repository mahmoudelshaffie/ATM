package org.atm.json;

import java.io.IOException;
import java.util.Iterator;

import org.atm.withdraw.CashWithdrawl;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class CashWithdrawlSerializer extends JsonSerializer<CashWithdrawl> {

	@Override
	public void serialize(CashWithdrawl cashWithdrawl, JsonGenerator gen, SerializerProvider pro) throws IOException {
		gen.writeStartObject();
		gen.writeFieldName("notes");
		gen.writeStartObject();
		Iterator<Short> notes = cashWithdrawl.getWithdrawedNotes();
		while(notes.hasNext()) {
			Short value = notes.next();
			Long amount = cashWithdrawl.getNoteAmount(value);
			gen.writeNumberField(value+"", amount);
		}
		gen.writeEndObject();
		gen.writeFieldName("coins");
		gen.writeStartObject();
		Iterator<Short> coins = cashWithdrawl.getWithdrawedCoins();
		while(coins.hasNext()) {
			Short value = coins.next();
			Long amount = cashWithdrawl.getCoinAmount(value);
			gen.writeNumberField(value+"", amount);
		}
		gen.writeEndObject();
		gen.writeEndObject();
	}
}
