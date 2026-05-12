package cn.lance.commons.util.json;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.util.StdDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class JacksonTest {

    @Test
    public void testDateSerialize() throws JacksonException {
        JsonMapper mapper = new JsonMapper();

        Date date = new Date();
        String dateJsonDefault = mapper.writeValueAsString(date);
        log.info("dateJsonDefault: {}", dateJsonDefault);

        mapper = mapper.rebuild()
                .defaultDateFormat(new StdDateFormat()
                        .withColonInTimeZone(true)
                        .withTimeZone(TimeZone.getDefault()))
                .build();
        String dateJsonStd = mapper.writeValueAsString(date);
        log.info("dateJsonStd: {}", dateJsonStd);

        mapper = mapper.rebuild()
                .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .build();
        String dateJsonSimple = mapper.writeValueAsString(date);
        log.info("dateJsonSimple: {}", dateJsonSimple);
    }

    @Test
    public void testJsr310LocalDateSerialize() throws JacksonException {
        JsonMapper mapper = new JsonMapper();

        LocalDate date = LocalDate.now();
        String dateJsonDefault = mapper.writeValueAsString(date);
        log.info("dateJsonDefault: {}", dateJsonDefault);

        mapper = mapper.rebuild()
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        String dateJsonDisabledTimestamp = mapper.writeValueAsString(date);
        log.info("dateJsonDisabledTimestamp: {}", dateJsonDisabledTimestamp);
    }

    @Test
    public void testJsr310LocalTimeSerialize() throws JacksonException {
        JsonMapper mapper = new JsonMapper();

        LocalTime time = LocalTime.now();
        String dateTimeJsonDefault = mapper.writeValueAsString(time);
        log.info("timeJsonDefault: {}", dateTimeJsonDefault);

        mapper = mapper.rebuild()
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        String dateTimeJsonDisabledTimestamp = mapper.writeValueAsString(time);
        log.info("timeJsonDisabledTimestamp: {}", dateTimeJsonDisabledTimestamp);

        SimpleModule module = new SimpleModule("JavaTimeModule");
        module.addSerializer(new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ));
        mapper = mapper.rebuild()
                .addModule(module)
                .build();
        String dateJson = mapper.writeValueAsString(time);
        log.info("timeJson: {}", dateJson);
    }

    @Test
    public void testJsr310LocalDateTimeSerialize() throws JacksonException {
        JsonMapper mapper = new JsonMapper();

        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeJsonDefault = mapper.writeValueAsString(dateTime);
        log.info("dateTimeJsonDefault: {}", dateTimeJsonDefault);

        mapper = mapper.rebuild()
                .disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        String dateTimeJsonDisabledTimestamp = mapper.writeValueAsString(dateTime);
        log.info("dateTimeJsonDisabledTimestamp: {}", dateTimeJsonDisabledTimestamp);

        SimpleModule module = new SimpleModule("JavaTimeModule");
        module.addSerializer(new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ));
        mapper = mapper.rebuild()
                .addModule(module)
                .build();
        String dateJson = mapper.writeValueAsString(dateTime);
        log.info("dateJson: {}", dateJson);
    }

}
