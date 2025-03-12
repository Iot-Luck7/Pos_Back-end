package com.Iot7_1team.pos_web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PosDTO {
    private String location;
    private Double latitude;
    private Double longitude;
    private String posLoginId;
    private String posPassword;
}
