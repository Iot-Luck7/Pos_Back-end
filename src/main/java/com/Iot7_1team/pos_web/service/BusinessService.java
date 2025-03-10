package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.repository.BusinessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 비즈니스 관리 서비스
 */
@Service
public class BusinessService {
    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    /**
     * 새로운 트랜잭션으로 비즈니스 저장
     * @param business - 저장할 비즈니스 객체
     * @return 저장된 비즈니스 객체 반환
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Business saveBusiness(Business business) {
        return businessRepository.save(business);
    }

    /**
     * 비즈니스 이름으로 조회
     * @param businessName - 조회할 비즈니스 이름
     * @return 존재하면 비즈니스 객체, 없으면 null 반환
     */
    public Business findByBusinessName(String businessName) {
        return businessRepository.findByBusinessName(businessName).orElse(null);
    }
}