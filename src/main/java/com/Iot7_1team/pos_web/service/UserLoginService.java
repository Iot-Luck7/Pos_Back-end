package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.PosLoginRequest;
import com.Iot7_1team.pos_web.model.BusinessUser;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserLoginService {

    private final PosRepository posRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserLoginService(PosRepository posRepository) {
        this.posRepository = posRepository;
    }

    public ResponseEntity<?> login(PosLoginRequest request) {
        Optional<Pos> posOptional = posRepository.findByPosLoginId(request.getPosLoginId());

        if (posOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인 실패"));
        }

        Pos pos = posOptional.get();
        BusinessUser businessUser = pos.getBusinessUser();

        if (!passwordEncoder.matches(request.getPosPassword(), pos.getPosPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "비밀번호가 일치하지 않습니다."));
        }

        return ResponseEntity.ok(Map.of(
                "message", "로그인 성공",
                "posId", pos.getPosId(),
                "businessId", businessUser.getBusinessId(),
                "businessType", businessUser.getBusinessType()
        ));
    }
}
