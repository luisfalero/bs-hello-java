package com.lfalero.hellojava.servicemesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class BsDateResponseDto {

    private Date dateBirth;
    private String version;
}