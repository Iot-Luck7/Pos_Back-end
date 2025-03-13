package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.BusinessUserDTO;
import com.Iot7_1team.pos_web.dto.PosDTO;
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
        PosDTO posDTO = requestDTO.getPos();

        // 1. 이메일(로그인 ID) 중복 체크
        if (posRepository.findByPosLoginId(posDTO.getPosLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. BUSINESS_USER DTO → Entity 변환 후 저장
        BusinessUserDTO businessDTO = requestDTO.getBusiness();
        BusinessUser businessUser = new BusinessUser();
        businessUser.setBusinessType(businessDTO.getBusinessType());
        businessUser.setBusinessName(businessDTO.getBusinessName());
        businessUser.setSponsorshipYn(businessDTO.getSponsorshipYn());

        BusinessUser savedBusinessUser = businessUserRepository.save(businessUser);

        // 3. POS DTO → Entity 변환 후 저장
        Pos pos = new Pos();
        pos.setBusinessUser(savedBusinessUser);
        pos.setLocation(posDTO.getLocation());
        pos.setLatitude(posDTO.getLatitude());
        pos.setLongitude(posDTO.getLongitude());
        pos.setPosLoginId(posDTO.getPosLoginId());
        pos.setPosPassword(posDTO.getPosPassword());

        posRepository.save(pos);
    }
}
