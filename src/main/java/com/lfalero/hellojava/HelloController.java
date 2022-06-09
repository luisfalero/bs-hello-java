package com.lfalero.hellojava;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bs")
public class HelloController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public BsResponseDto hello() {
        String host = System.getenv().getOrDefault("HOSTNAME", "unknown");
        String description = "Hello world from host API BS";
        return new BsResponseDto(host, description);
    }
}
