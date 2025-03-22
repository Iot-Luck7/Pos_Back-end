package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MENU")
@SequenceGenerator(name = "menu_seq_gen", sequenceName = "IOT7.SEQ_MENU_ID", allocationSize = 1)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq_gen")
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "BUSINESS_ID", nullable = false)
    private Long businessId;

    @Column(name = "MENU_NAME", nullable = false)
    private String menuName;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CALORIE")
    private Double calorie;

    @Column(name = "INGREDIENTS")
    private String ingredients;

    @Column(name = "DIET_YN")
    private String dietYn;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;
}
