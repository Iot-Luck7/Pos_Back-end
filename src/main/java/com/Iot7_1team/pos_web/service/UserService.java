package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void registerUser(Business business, Pos pos) {
        try {
            // 1️⃣ BUSINESS_USER 중복 체크 후 저장
            Business existingBusiness = businessService.findByBusinessName(business.getBusinessName());
            Business savedBusiness;

            if (existingBusiness != null) {
                System.out.println("Existing BUSINESS found with ID: " + existingBusiness.getBusinessId());
                savedBusiness = existingBusiness;
            } else {
                savedBusiness = businessService.saveBusiness(business);
                System.out.println("New BUSINESS created with ID: " + savedBusiness.getBusinessId());
            }

            // 2️⃣ 강제 flush() 실행하여 DB 반영
            entityManager.flush();

            // 🔥 DB에서 다시 `BUSINESS_ID` 확인
            savedBusiness = entityManager.find(Business.class, savedBusiness.getBusinessId());

            // 3️⃣ POS의 Business 관계 설정
            pos.setBusiness(savedBusiness);
            System.out.println("Setting POS business ID to: " + pos.getBusiness().getBusinessId());

            // 4️⃣ POS 저장
            posService.savePos(pos);
        } catch (Exception e) {
            System.out.println("Error occurred while saving POS, rolling back entire transaction.");
            throw new RuntimeException("POS 저장 중 오류 발생: " + e.getMessage());
        }
    }
}
