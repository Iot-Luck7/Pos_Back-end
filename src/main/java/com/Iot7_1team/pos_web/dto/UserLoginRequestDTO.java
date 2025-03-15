package com.Iot7_1team.pos_web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginRequestDTO {
    private String posLoginId;
    private String posPassword;
}
