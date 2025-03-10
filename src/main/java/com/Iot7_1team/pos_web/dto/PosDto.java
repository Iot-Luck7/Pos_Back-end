package com.Iot7_1team.pos_web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosDto {
    private String posLoginId;
    private String posPassword;
    private Double latitude;
    private Double longitude;
}
