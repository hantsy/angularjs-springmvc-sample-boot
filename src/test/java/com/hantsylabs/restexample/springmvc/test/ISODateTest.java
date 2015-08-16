package com.hantsylabs.restexample.springmvc.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hantsylabs.restexample.springmvc.Application;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import javax.servlet.ServletException;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ISODateTest {

    private static final Logger log = LoggerFactory.getLogger(ISODateTest.class);

    @Inject
    private ObjectMapper objectMapper;

    private LocalDateTime date;

    private OffsetDateTime offsetDate;

    private ZonedDateTime zonedDate;

    @Before
    public void setup() throws ServletException {
        date = LocalDateTime.of(2015, 8, 15, 11, 40, 10, 100_000_000);
        offsetDate = OffsetDateTime.of(2015, 8, 15, 11, 40, 10, 100_000_000, ZoneOffset.ofHours(8));
        zonedDate = ZonedDateTime.of(2015, 8, 15, 11, 40, 10, 100_000_000, ZoneId.of("Asia/Shanghai"));
    }

    @Test
    public void testDateFormat() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("localDate", date);
        map.put("offsetDate", offsetDate);
        map.put("zonedDate", zonedDate);

        String json = objectMapper.writeValueAsString(map);

        log.debug("converted json result @" + json);

        JsonNode rootNode = objectMapper.readTree(json);

        JsonNode localDateNode = rootNode.get("localDate");
        JsonNode offsetDateNode = rootNode.get("offsetDate");
        JsonNode zonedDateNode = rootNode.get("zonedDate");

        log.debug("LocalDateTime format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)@" + date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        log.debug("LocalDateTime toString                                     @" + date.toString());
        log.debug("LocalDateTime serialized json node text                    @" + localDateNode.textValue());

        log.debug("OffsetDateTime format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)@" + offsetDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        log.debug("OffsetDateTime toString                                      @" + offsetDate.toString());
        log.debug("OffsetDateTime serialized json node text                     @" + offsetDateNode.textValue());

        log.debug("ZonedDateTime format(DateTimeFormatter.ISO_ZONED_DATE_TIME)@" + zonedDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        log.debug("ZonedDateTime toString                                     @" + zonedDate.toString());
        log.debug("ZonedDateTime serialized json node text                    @" + zonedDateNode.textValue());

        assertEquals("local date should be equals", date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), localDateNode.textValue());
        assertEquals("offsetDate date should be equals", offsetDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), offsetDateNode.textValue());
        assertEquals("zonedDate date should be equals", zonedDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME), zonedDateNode.textValue());

    }
}
