package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "BUSINESS_USER")  // DB 테이블명과 일치하도록 수정
@SequenceGenerator(
        name = "business_seq_generator",
        sequenceName = "SEQ_BUSINESS_ID",  // DB 시퀀스명과 맞춤
        allocationSize = 1
)
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_seq_generator")
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    @Column(name = "BUSINESS_TYPE", nullable = false)
    private String businessType;

    @Column(name = "BUSINESS_NAME", nullable = false, unique = true)
    private String businessName;

    @Column(name = "SPONSORSHIP_YN", nullable = false, length = 1, columnDefinition = "CHAR(1)")
    private String sponsorshipYn = "N"; // 기본값 'N' 설정

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pos> posList = new ArrayList<>();
}
