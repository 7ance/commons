package cn.lance.commons.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    public void testDateSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Date date = new Date();
        String dateJsonDefault = mapper.writeValueAsString(date);
        log.info("dateJsonDefault: {}", dateJsonDefault);
        log.info("Default DateFormat: {}", mapper.getDateFormat());

        mapper.setDateFormat(
                new StdDateFormat()
                        .withColonInTimeZone(true)
                        .withTimeZone(TimeZone.getDefault())
        );
        String dateJsonStd = mapper.writeValueAsString(date);
        log.info("dateJsonStd: {}", dateJsonStd);
        log.info("Standard DateFormat: {}", mapper.getDateFormat());

        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String dateJsonSimple = mapper.writeValueAsString(date);
        log.info("dateJsonSimple: {}", dateJsonSimple);
        log.info("Simple DateFormat: {}", mapper.getDateFormat());
    }

    @Test
    public void testJsr310LocalDateSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        LocalDate date = LocalDate.now();
        String dateJsonDefault = mapper.writeValueAsString(date);
        log.info("dateJsonDefault: {}", dateJsonDefault);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String dateJsonDisabledTimestamp = mapper.writeValueAsString(date);
        log.info("dateJsonDisabledTimestamp: {}", dateJsonDisabledTimestamp);
    }

    @Test
    public void testJsr310LocalTimeSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        LocalTime time = LocalTime.now();
        String dateTimeJsonDefault = mapper.writeValueAsString(time);
        log.info("timeJsonDefault: {}", dateTimeJsonDefault);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String dateTimeJsonDisabledTimestamp = mapper.writeValueAsString(time);
        log.info("timeJsonDisabledTimestamp: {}", dateTimeJsonDisabledTimestamp);

        // module只能register一次，所以重新创建一个ObjectMapper实例
        mapper = new ObjectMapper();

        LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        module.addSerializer(serializer);
        mapper.registerModule(module);
        String dateJson = mapper.writeValueAsString(time);
        log.info("timeJson: {}", dateJson);
    }

    @Test
    public void testJsr310LocalDateTimeSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        LocalDateTime dateTime = LocalDateTime.now();
        String dateTimeJsonDefault = mapper.writeValueAsString(dateTime);
        log.info("dateTimeJsonDefault: {}", dateTimeJsonDefault);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String dateTimeJsonDisabledTimestamp = mapper.writeValueAsString(dateTime);
        log.info("dateTimeJsonDisabledTimestamp: {}", dateTimeJsonDisabledTimestamp);

        // module只能register一次，所以重新创建一个ObjectMapper实例
        mapper = new ObjectMapper();

        LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        module.addSerializer(serializer);
        mapper.registerModule(module);
        String dateJson = mapper.writeValueAsString(dateTime);
        log.info("dateJson: {}", dateJson);
    }


}
