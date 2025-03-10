package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.repository.BusinessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Business saveBusiness(Business business) {
        return businessRepository.save(business);
    }

    public Business findByBusinessName(String businessName) {
        return businessRepository.findByBusinessName(businessName).orElse(null);
    }
}

