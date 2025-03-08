package com.Iot7_1team.pos_web.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    private BusinessDto business;
    private PosDto pos;
}

@Getter @Setter
class BusinessDto {
    private String businessType;
    private String businessName;
    private String sponsorshipYn;
}

@Getter @Setter
class PosDto {
    private String posLoginId;
    private String posPassword;
    private Double latitude;
    private Double longitude;
}
