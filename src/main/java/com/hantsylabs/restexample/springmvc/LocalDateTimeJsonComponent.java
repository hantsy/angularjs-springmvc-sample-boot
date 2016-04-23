package com.hantsylabs.restexample.springmvc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;

/**
 *
 * @author hantsy
 */
@JsonComponent
@Slf4j
public class LocalDateTimeJsonComponent {

    public static class LocalDateTimeSerializer extends JsonObjectSerializer<LocalDateTime> {

        @Override
        protected void serializeObject(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(Instant.from(value).toString());
        }

    }

    public static class LocalDateTimeDeserializer extends JsonObjectDeserializer<LocalDateTime> {

        @Override
        protected LocalDateTime deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {

            String dateTimeAsString = tree.textValue();
            log.debug("dateTimeString value @" + dateTimeAsString);
            return LocalDateTime.ofInstant(Instant.parse(dateTimeAsString), ZoneId.systemDefault());
        }

    }
}
