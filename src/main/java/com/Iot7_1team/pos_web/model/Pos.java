package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "POS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POS_ID")
    private Long posId;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    private BusinessUser businessUser;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "POS_LOGIN_ID", nullable = false, unique = true)
    private String posLoginId;

    @Column(name = "POS_PASSWORD", nullable = false)
    private String posPassword;
}
