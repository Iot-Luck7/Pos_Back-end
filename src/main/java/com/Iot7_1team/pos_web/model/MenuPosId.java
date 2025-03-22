package com.Iot7_1team.pos_web.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuPosId implements Serializable {
    private Long menuId;
    private Long posId;
}
