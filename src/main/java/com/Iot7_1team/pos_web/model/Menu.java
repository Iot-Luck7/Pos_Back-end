package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@Entity
@Table(name = "MENU")
@SequenceGenerator(
        name = "menu_seq_generator",
        sequenceName = "SEQ_MENU_ID",
        allocationSize = 1
)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq_generator")
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "MENU_NAME", nullable = false, length = 100)
    private String menuName;

    @Column(name = "CATEGORY", length = 50)
    private String category;

    @Column(name = "PRICE", precision = 10, scale = 2)
    private BigDecimal price;  // Double → BigDecimal (NUMBER(10,2) 대응)

    @Column(name = "CLICK_COUNT")
    private Long clickCount; // Integer → Long (NUMBER(10,0) 대응)

    @Column(name = "CALORIE", precision = 10, scale = 2)
    private BigDecimal calorie; // Double → BigDecimal (NUMBER(10,2) 대응)

    @Column(name = "INGREDIENTS", length = 1000)
    private String ingredients;

    @Column(name = "DIET_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String dietYn = "N";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REG_DATE", nullable = false)
    private Date regDate = new Date();

    @ManyToOne
    @JoinColumn(name = "BUSINESS_NAME", referencedColumnName = "BUSINESS_NAME", nullable = false)
    private Business business; // 외래키 매핑
}
