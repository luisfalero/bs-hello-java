package com.lfalero.hellojava;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("bs")
public class HelloController {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    BsDateResponseDto bsDateResponseDto;

    @Autowired
    private RequestService requestService;

    @Value("${system.user1}")
    private String user1;

    @Value("${system.user2}")
    private String user2;

    @Value("${system.password1}")
    private String password1;

    @Value("${system.password2}")
    private String password2;

    @GetMapping(value = "/v1/timer", produces = MediaType.APPLICATION_JSON_VALUE)
    public String timerV1(@RequestParam Integer millis) {
        log.info("Endpoint = [{}]:", "/v1/timer");
        try {
            log.info("Message = [{}, {}]:", "TimerTask started", millis);
            Thread.sleep(millis);
            log.info("Message = [{}, {}]:", "TimerTask ended", millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "TimerTask ended = ".concat(millis.toString());
    }

    @GetMapping(value = "/v1/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto helloV1(HttpServletRequest request) {
        log.info("Endpoint = [{}]:", "/v1/hello");
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API v1 BS";
        String clientIp = requestService.getClientIp(request);
        log.info("Message = [{}, {}, {}]:", new Object[] {clientIp, host, description});
        return new BsResponseDto(clientIp, host, description, "v1");
    }

    @GetMapping(value = "/v2/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto helloV2(HttpServletRequest request) {
        log.info("Endpoint = [{}]:", "/v2/hello");
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API v2 BS";
        String clientIp = requestService.getClientIp(request);
        log.info("Message = [{}, {}, {}]:", new Object[] {clientIp, host, description});
        return new BsResponseDto(clientIp, host, description, "v2");
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

    @GetMapping(value = "/v1/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsEnvironmentResponseDto environmentV1() {
        log.info("Endpoint = [{}]:", "/v1/environment");
        log.info("Message = [{}, {}, {}, {}]:", new Object[] {user1, user2, password1, password2});
        return new BsEnvironmentResponseDto(user1, user2, password1, password2, "v1");
    }

    @GetMapping(value = "/v2/environment", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsEnvironmentResponseDto environmentV2() {
        log.info("Endpoint = [{}]:", "/v2/environment");
        log.info("Message = [{}, {}, {}, {}]:", new Object[] {user1, user2, password1, password2});
        return new BsEnvironmentResponseDto(user1, user2, password1, password2, "v2");
    }

    @GetMapping(value = "/v1/propagation", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto propagationV1(HttpServletRequest request, @RequestParam("description") String description) {
        log.info("Endpoint = [{}]:", "/v1/propagation");
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String clientIp = requestService.getClientIp(request);
        log.info("Message = [{}, {}, {}]:", new Object[] {clientIp, host, description});
        return new BsResponseDto(clientIp, host, description, "v1");
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
