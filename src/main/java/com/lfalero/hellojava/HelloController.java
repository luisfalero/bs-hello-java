package com.lfalero.hellojava;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    BsDateResponseDto bsDateResponseDto;
    @GetMapping(value = "/v1/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto helloV1() {
        log.info("Endpoint = [{}]:", "/v1/hello");
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API v1 BS";
        log.info("Message = [{}, {}]:", new Object[] {host, description});
        return new BsResponseDto(host, description, "v1");
    }

    @GetMapping(value = "/v2/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto helloV2() {
        log.info("Endpoint = [{}]:", "/v2/hello");
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API v2 BS";
        log.info("Message = [{}, {}]:", new Object[] {host, description});
        return new BsResponseDto(host, description, "v2");
    }

    @GetMapping(value = "/v1/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsDateResponseDto dateV1() {
        log.info("Endpoint = [{}]:", "/v1/date");
        bsDateResponseDto = new BsDateResponseDto(this.dateFormat(), "v1");
        return bsDateResponseDto;
    }

    @GetMapping(value = "/v2/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsDateResponseDto dateV2() {
        log.info("Endpoint = [{}]:", "/v2/date");
        bsDateResponseDto = new BsDateResponseDto(this.dateFormat(), "v2");
        return bsDateResponseDto;
    }

    private Date dateFormat() {
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
        return date;
    }
}
