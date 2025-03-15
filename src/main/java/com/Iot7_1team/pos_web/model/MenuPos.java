package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MENU_POS")
public class MenuPos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MENU_ID", nullable = false)
    private Long menuId;

    @Column(name = "POS_ID", nullable = false)
    private Long posId;

    @Column(name = "SALE_STATUS", nullable = false)
    private Integer saleStatus = 1; // 1: 판매중, 0: 미판매
}
