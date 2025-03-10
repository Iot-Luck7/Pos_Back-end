package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저 등록 서비스
 */
@Service
public class UserService {
    private final BusinessService businessService;
    private final PosService posService;
    private final EntityManager entityManager;

    public UserService(BusinessService businessService, PosService posService, EntityManager entityManager) {
        this.businessService = businessService;
        this.posService = posService;
        this.entityManager = entityManager;
    }

    /**
     * 유저 등록
     * @param business - 등록할 비즈니스 객체
     * @param pos - 등록할 POS 객체
     */
    @Transactional
    public void registerUser(Business business, Pos pos) {
        Business savedBusiness = businessService.findByBusinessName(business.getBusinessName());
        if (savedBusiness == null) {
            savedBusiness = businessService.saveBusiness(business);
        }

        entityManager.flush(); // DB 반영
        savedBusiness = entityManager.find(Business.class, savedBusiness.getBusinessId());
        pos.setBusiness(savedBusiness);
        posService.savePos(pos);
    }
}