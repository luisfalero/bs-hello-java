package com.lfalero.hellojava;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
