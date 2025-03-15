package com.Iot7_1team.pos_web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRegisterRequestDTO {
    private String posLoginId;
    private String menuName;
    private String category;
    private int price;
    private int calorie;
    private String ingredients;
    private boolean dietYn;
}

