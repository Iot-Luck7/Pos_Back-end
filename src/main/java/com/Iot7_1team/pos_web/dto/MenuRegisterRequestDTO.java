package com.Iot7_1team.pos_web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRegisterRequestDTO {
    private String posLoginId;
    private String menuName;
    private String category;
    private Double price;
    private Double calorie;
    private String ingredients;
    private boolean dietYn;
}

