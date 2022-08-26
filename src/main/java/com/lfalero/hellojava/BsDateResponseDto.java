package com.lfalero.hellojava;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class BsDateResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date dateBirth;
}
