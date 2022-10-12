package com.lfalero.hellojava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BsEnvironmentResponseDto {

    private String user;
    private String password;
    private String version;
}
