package com.lfalero.hellojava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BsEnvironmentResponseDto {

    private String user1;
    private String user2;
    private String password1;
    private String password2;
    private String version;
}
