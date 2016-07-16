package com.hantsylabs.restexample.springmvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 *
 * @author hantsy
 */
@Configuration
public class Jackson2ObjectMapperConfig {
        
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(JsonComponentModule jsonComponentModule) {
   
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
            builder
    //                .serializerByType(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
    //                    @Override
    //                    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    //                        jsonGenerator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
    //                    }
    //                })
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                )
                .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .indentOutput(true)
                .modulesToInstall(jsonComponentModule);
    
        return builder;
    } 
    
}
