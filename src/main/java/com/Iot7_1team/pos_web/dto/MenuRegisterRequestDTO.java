package com.Iot7_1team.pos_web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRegisterRequestDTO {
    private String menuName;
    private String category;
    private Double price;
    private Double calorie;      // 프론트에서 calories로 보내는 값
    private boolean dietYn;      // isDiet와 매핑됨
    private String imageUrl;
    private String description;
}
