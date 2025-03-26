package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.UserRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.BusinessUser;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.BusinessUserRepository;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 이 클래스가 서비스 레이어 컴포넌트임을 명시
public class UserRegisterService {

    private final BusinessUserRepository businessUserRepository; // 사업자 저장/조회용 리포지토리
    private final PosRepository posRepository; // POS 저장/조회용 리포지토리
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 도구

    // 생성자 주입
    public UserRegisterService(BusinessUserRepository businessUserRepository, PosRepository posRepository, PasswordEncoder passwordEncoder) {
        this.businessUserRepository = businessUserRepository;
        this.posRepository = posRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 유저(POS 및 사업자) 등록 서비스
     *
     * @param requestDTO 클라이언트에서 전달된 등록 정보
     */
    @Transactional // 트랜잭션 처리: 예외 발생 시 전체 롤백
    public void registerUser(UserRegisterRequestDTO requestDTO) {
        String businessType = requestDTO.getBusiness().getBusinessType(); // 본점 / 개인 여부
        String businessName = requestDTO.getBusiness().getBusinessName(); // 사업장 이름
        String posLoginId = requestDTO.getPos().getPosLoginId();         // POS 로그인 ID

        // 1️⃣ POS_LOGIN_ID 중복 검사
        if (posRepository.findByPosLoginId(posLoginId).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 POS 로그인 ID입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDTO.getPos().getPosPassword());

        // 2️⃣ 본점 등록 시 같은 이름의 본점이 이미 존재하는지 확인
        if ("본점".equals(businessType)) {
            List<BusinessUser> existingBusinesses = businessUserRepository.findByBusinessTypeAndBusinessName("본점", businessName);
            if (!existingBusinesses.isEmpty()) {
                throw new IllegalArgumentException("본점은 같은 BUSINESS_NAME을 가질 수 없습니다.");
            }
        }

        // 3️⃣ 개인 등록 시, 동일한 이름의 본점이 이미 존재하는지 확인
        if ("개인".equals(businessType)) {
            List<BusinessUser> existingMainBusinesses = businessUserRepository.findByBusinessTypeAndBusinessName("본점", businessName);
            if (!existingMainBusinesses.isEmpty()) {
                throw new IllegalArgumentException("개인은 기존 본점의 BUSINESS_NAME과 중복될 수 없습니다.");
            }
        }

        // 4️⃣ BUSINESS_USER(사업자 정보) 저장
        BusinessUser businessUser = new BusinessUser();
        businessUser.setBusinessType(businessType);
        businessUser.setBusinessName(businessName);
        businessUser.setSponsorshipYn(requestDTO.getBusiness().getSponsorshipYn()); // 스폰 여부
        BusinessUser savedBusinessUser = businessUserRepository.save(businessUser);

        // 5️⃣ POS 정보 저장
        Pos pos = new Pos();
        pos.setBusinessUser(savedBusinessUser); // 외래 키 관계 설정
        pos.setLocation(requestDTO.getPos().getLocation());
        pos.setLatitude(requestDTO.getPos().getLatitude());
        pos.setLongitude(requestDTO.getPos().getLongitude());
        pos.setPosLoginId(requestDTO.getPos().getPosLoginId());
        pos.setPosPassword(encodedPassword); // 암호화된 비밀번호 저장

        posRepository.save(pos); // 최종 저장
    }
}
