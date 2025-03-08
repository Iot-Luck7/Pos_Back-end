package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.repository.BusinessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 새로운 트랜잭션으로 실행
    public Business saveBusiness(Business business) {
        return businessRepository.save(business);
    }

    public Business findByBusinessName(String businessName) {
        Optional<Business> existingBusiness = businessRepository.findByBusinessName(businessName);
        return existingBusiness.orElse(null);
    }
}
