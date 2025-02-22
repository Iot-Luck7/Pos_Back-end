package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "Business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Long businessId;

    @Column(name = "business_type", nullable = false)
    private String businessType;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "spon", nullable = false)
    private String spon;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pos> posList = new ArrayList<>();
}
