package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "POS")
@SequenceGenerator(
        name = "pos_seq_generator",
        sequenceName = "SEQ_POS_ID",  // DB 시퀀스명과 일치
        allocationSize = 1
)
public class Pos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pos_seq_generator")
    @Column(name = "POS_ID")
    private Long posId;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    private Business business; // 비즈니스와 관계 설정

    @Column(name = "POS_LOGIN_ID", unique = true, nullable = false)
    private String posLoginId;

    @Column(name = "POS_PASSWORD", nullable = false)
    private String posPassword;

    @Column(name = "LATITUDE", columnDefinition = "NUMBER(10,6)")
    private Double latitude;

    @Column(name = "LONGITUDE", columnDefinition = "NUMBER(10,6)")
    private Double longitude;

}
