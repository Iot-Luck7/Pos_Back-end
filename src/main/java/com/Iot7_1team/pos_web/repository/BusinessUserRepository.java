package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessUserRepository extends JpaRepository<BusinessUser, Long> {

    /**
     * 특정 BUSINESS_TYPE과 BUSINESS_NAME을 가진 BusinessUser가 존재하는지 조회하는 메서드.
     * - '본점'이 같은 BUSINESS_NAME을 가질 경우 제한할 때 사용됨.
     * - '개인'이 기존 '본점'의 BUSINESS_NAME과 중복되지 않도록 제한할 때 사용됨.
     *
     * @param businessType 조회할 사업 유형 (예: "본점", "개인")
     * @param businessName 조회할 사업장 이름
     * @return 동일한 BUSINESS_TYPE과 BUSINESS_NAME을 가진 BusinessUser 리스트 반환
     */
    List<BusinessUser> findByBusinessTypeAndBusinessName(String businessType, String businessName);
    Optional<BusinessUser> findByBusinessId(Long businessId);

}
