package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "POS")
public class Pos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_id")
    private Long posId;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "password", nullable = false)
    private String password;
}
