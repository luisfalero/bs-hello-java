package com.lfalero.hellojava.servicemesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BsResponseDto {

    private String clientIp;
    private String host;
    private String description;
    private String version;
}