package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    private final PosRepository posRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoginService(PosRepository posRepository, PasswordEncoder passwordEncoder) {
        this.posRepository = posRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Long> authenticate(String loginId, String rawPassword) {
        Optional<Pos> posOptional = posRepository.findByPosLoginId(loginId);

        if (posOptional.isPresent()) {
            Pos pos = posOptional.get();
            System.out.println("📌 DB 저장된 해시 비밀번호: " + pos.getPosPassword()); // ✅ 로그 추가
            System.out.println("📌 입력된 비밀번호: " + rawPassword); // ✅ 로그 추가
            boolean matches = passwordEncoder.matches(rawPassword, pos.getPosPassword());
            System.out.println("✅ 비밀번호 일치 여부: " + matches);

            if (matches) {
                System.out.println("✅ 로그인 성공! BUSINESS_ID: " + pos.getBusinessId()); // ✅ businessId 로그 추가
                return Optional.of(pos.getBusinessId()); // ✅ businessId 반환
            }
        }
        return Optional.empty();
    }
}
