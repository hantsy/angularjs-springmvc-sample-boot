package com.hantsylabs.restexample.springmvc.test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author hantsy
 */
@RunWith(SpringRunner.class)
@JsonTest()
//@SpringBootTest
//@AutoConfigureJsonTesters
@Slf4j
public class LocalDateTimeJsonComponentTest {

    @Inject
    Jackson2ObjectMapperBuilder mapper;

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
    static class TimeObj {

        private LocalDateTime now;
    }

}
