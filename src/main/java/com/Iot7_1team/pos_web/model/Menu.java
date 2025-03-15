package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MENU")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq")
    @SequenceGenerator(name = "menu_seq", sequenceName = "SEQ_MENU_ID", allocationSize = 1)
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "MENU_NAME", nullable = false)
    private String menuName;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "PRICE", nullable = false)
    private Double price; // 🔹 Integer → Double로 수정

    @Column(name = "CLICK_COUNT")
    private Integer clickCount = 0;

    @Column(name = "CALORIE")
    private Double calorie;

    @Column(name = "INGREDIENTS")
    private String ingredients;

    @Column(name = "DIET_YN", nullable = false)
    private String dietYn = "N";

    @Column(name = "REG_DATE", nullable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @Column(name = "BUSINESS_ID", nullable = false)
    private Long businessId;

    // 🔹 복사 생성자 추가
    public Menu(Menu menu, Long businessId) {
        this.menuName = menu.getMenuName();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.clickCount = 0; // 신규 메뉴이므로 초기화
        this.calorie = menu.getCalorie();
        this.ingredients = menu.getIngredients();
        this.dietYn = menu.getDietYn();
        this.regDate = LocalDateTime.now();
        this.businessId = businessId;
    }
}
