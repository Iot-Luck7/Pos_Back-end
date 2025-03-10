package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.RegisterRequest;
import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final BusinessService businessService;
    private final PosService posService;

    @PersistenceContext
    private EntityManager entityManager;
    public UserService(BusinessService businessService, PosService posService) {
        this.businessService = businessService;
        this.posService = posService;
    }

    @Transactional
    public void registerUser(RegisterRequest request) {
        // 1️⃣ BUSINESS_USER 무조건 새로 생성
        Business newBusiness = new Business();
        newBusiness.setBusinessType(request.getBusiness().getBusinessType());
        newBusiness.setBusinessName(request.getBusiness().getBusinessName());
        newBusiness.setSponsorshipYn(request.getBusiness().getSponsorshipYn());

        Business savedBusiness = businessService.saveBusiness(newBusiness);

        entityManager.flush(); // 🔥 DB 반영 (즉시 BUSINESS_USER 저장)

        // 2️⃣ POS 저장
        Pos pos = new Pos();
        pos.setBusiness(savedBusiness);
        pos.setPosLoginId(request.getPos().getPosLoginId());
        pos.setPosPassword(request.getPos().getPosPassword());
        pos.setLatitude(request.getPos().getLatitude());
        pos.setLongitude(request.getPos().getLongitude());

        posService.savePos(pos);
    }

}
