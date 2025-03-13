package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.UserRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.BusinessUser;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.BusinessUserRepository;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterService {

    private final BusinessUserRepository businessUserRepository;
    private final PosRepository posRepository;

    public UserRegisterService(BusinessUserRepository businessUserRepository, PosRepository posRepository) {
        this.businessUserRepository = businessUserRepository;
        this.posRepository = posRepository;
    }

    @Transactional
    public void registerUser(UserRegisterRequestDTO requestDTO) {
        String businessType = requestDTO.getBusiness().getBusinessType();
        String businessName = requestDTO.getBusiness().getBusinessName();
        String posLoginId = requestDTO.getPos().getPosLoginId();

        // 1️⃣ POS_LOGIN_ID 중복 검사
        if (posRepository.findByPosLoginId(posLoginId).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 POS 로그인 ID입니다.");
        }

        // 2️⃣ BUSINESS_TYPE이 '본점'이면 BUSINESS_NAME 중복 검사
        if ("본점".equals(businessType) && businessUserRepository.findByBusinessTypeAndBusinessName("본점", businessName).isPresent()) {
            throw new IllegalArgumentException("본점은 같은 BUSINESS_NAME을 가질 수 없습니다.");
        }

        // 3️⃣ BUSINESS_USER 저장
        BusinessUser businessUser = new BusinessUser();
        businessUser.setBusinessType(businessType);
        businessUser.setBusinessName(businessName);
        businessUser.setSponsorshipYn(requestDTO.getBusiness().getSponsorshipYn());
        BusinessUser savedBusinessUser = businessUserRepository.save(businessUser);

        // 4️⃣ POS 저장
        Pos pos = new Pos();
        pos.setBusinessUser(savedBusinessUser);
        pos.setLocation(requestDTO.getPos().getLocation());
        pos.setLatitude(requestDTO.getPos().getLatitude());
        pos.setLongitude(requestDTO.getPos().getLongitude());
        pos.setPosLoginId(requestDTO.getPos().getPosLoginId());
        pos.setPosPassword(requestDTO.getPos().getPosPassword());

        posRepository.save(pos);
    }
}
