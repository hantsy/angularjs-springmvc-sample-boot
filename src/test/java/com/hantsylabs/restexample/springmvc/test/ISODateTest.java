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
        assertEquals("local date should be equals", date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), localDateNode.textValue());

        JsonNode offsetDateNode = rootNode.get("offsetDate");
        assertEquals("offsetDate date should be equals", offsetDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), offsetDateNode.textValue());

        JsonNode zonedDateNode = rootNode.get("zonedDate");
        assertEquals("zonedDate date should be equals", zonedDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME), zonedDateNode.textValue());

    }
}
