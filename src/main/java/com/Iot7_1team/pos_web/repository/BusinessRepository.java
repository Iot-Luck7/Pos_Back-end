package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByBusinessName(String businessName);
}
