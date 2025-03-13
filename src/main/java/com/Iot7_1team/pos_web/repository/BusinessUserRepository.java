package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {
    Optional<BusinessUser> findByBusinessTypeAndBusinessName(String businessType, String businessName);
}
