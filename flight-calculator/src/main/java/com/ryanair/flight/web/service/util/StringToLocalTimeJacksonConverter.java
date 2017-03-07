package com.ryanair.flight.web.service.util;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * <p>Converts String to LocalTime in a Jackon serialization</p>
 * @author a452051
 *
 */
public class StringToLocalTimeJacksonConverter extends JsonDeserializer<LocalTime>{
	
	/*
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public LocalTime deserialize(JsonParser jsonParser, DeserializationContext desearilationContext) throws IOException, JsonProcessingException {
		String time = jsonParser.readValueAs(String.class);
		String[] chunks = time.split(":");
		return LocalTime.of(Integer.valueOf(chunks[0]), Integer.valueOf(chunks[1]), 00);
	}
	

}
