package com.lfalero.hellojava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("bs")
public class HelloController {

    @GetMapping(value = "/v1/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto helloV1() {
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API v1 BS";
        return new BsResponseDto(host, description);
    }

    @GetMapping(value = "/v2/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto helloV2() {
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API v2 BS";
        return new BsResponseDto(host, description);
    }

    @GetMapping(value = "/v1/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsDateResponseDto dateV1() {
        String dateString = "19871104";
        String dateFormat = "yyyyMMdd";
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            date = formatter.parse(dateString);
            log.info("Date = [{}]:", date.getTime());
            log.info("Get Time = [{}]:", new Date(date.getTime()));

        } catch (Exception e) {
            log.error("Error when try to convert date with dateString = [{}], Stack Trace:", dateString, e);
        }
        return new BsDateResponseDto(date);
    }
}
