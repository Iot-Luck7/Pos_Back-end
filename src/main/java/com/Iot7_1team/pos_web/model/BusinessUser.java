package com.Iot7_1team.pos_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BUSINESS_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_ID")
    private Long businessId;

    @Column(name = "BUSINESS_TYPE", nullable = false)
    private String businessType;

    @Column(name = "BUSINESS_NAME", nullable = false)
    private String businessName;

    @Column(name = "SPONSORSHIP_YN", columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String sponsorshipYn;
}
