package com.hantsylabs.restexample.springmvc.test.slice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author hantsy
 */
@RunWith(SpringRunner.class)
@JsonTest()
@Slf4j
public class LocalDateTimeJsonComponentTest {

    @Inject
    JacksonTester<TimeObj> tester;

    @Inject
    Jackson2ObjectMapperBuilder mapper;

    @Test
    public void testJsonWithJacksonTester() throws Exception {

        assertNotNull(tester);

        String dateTimeStirng = "2016-03-29T20:05:01.101Z";
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(dateTimeStirng), ZoneId.systemDefault());
        TimeObj obj = new TimeObj(dateTime);

        assertThat(tester.write(obj).getJson()).containsIgnoringCase("{\"now\":\"2016-03-29T20:05:01.101Z\"}");

        TimeObj parsedDateTime = tester.parseObject("{\"now\":\"2016-03-29T20:05:01.101Z\"}");
        log.debug("parsed Date time @" + parsedDateTime);

        assertTrue(dateTime.equals(parsedDateTime.getNow()));
    }

    @Test
    public void testJson() throws Exception {

        assertNotNull(mapper);
        LocalDateTime dateTime = LocalDateTime.now();
        Map test = new HashMap();
        test.put("now", dateTime);

        String formattedDTString = mapper.build().writeValueAsString(test);
        log.debug("formated datetime @" + formattedDTString);

        TimeObj parsedDateTimeMap = mapper.build().readValue("{\"now\":\"2016-03-29T20:05:01.101Z\"}", TimeObj.class);
        log.debug("parsed Date time @" + parsedDateTimeMap);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class TimeObj {

        private LocalDateTime now;
    }

}
