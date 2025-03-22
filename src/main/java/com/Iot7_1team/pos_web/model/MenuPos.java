package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MENU_POS")
public class MenuPos {
    @EmbeddedId
    private MenuPosId id;

    @Column(name = "SALE_STATUS", nullable = false)
    private Integer saleStatus = 1;
}

