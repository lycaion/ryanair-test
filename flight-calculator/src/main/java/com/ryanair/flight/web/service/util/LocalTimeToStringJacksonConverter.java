package com.ryanair.flight.web.service.util;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**
 * <p>Convert the {@link LocalDateTime} to String</p>
 * @author angel
 *
 */
public class LocalTimeToStringJacksonConverter extends JsonSerializer<LocalDateTime>{
	/*
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(LocalDateTime time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeString(time.toString());
	}

}
