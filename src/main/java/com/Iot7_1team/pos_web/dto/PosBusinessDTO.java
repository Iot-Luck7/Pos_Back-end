package com.Iot7_1team.pos_web.dto;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PosBusinessDTO {
    private Business business;
    private Pos pos;
}
