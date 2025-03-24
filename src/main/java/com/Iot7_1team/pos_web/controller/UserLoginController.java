package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.PosLoginRequest;
import com.Iot7_1team.pos_web.model.BusinessUser;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import com.Iot7_1team.pos_web.service.UserLoginService;
import com.Iot7_1team.pos_web.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pos")
@CrossOrigin(origins = "http://localhost:3000")
public class UserLoginController {

    private final PosRepository posRepository; // ✅ 추가

    public UserLoginController(UserLoginService userLoginService, JwtUtil jwtUtil, PosRepository posRepository) {
        this.posRepository = posRepository; // ✅ 주입
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PosLoginRequest request) {
        Optional<Pos> posOptional = posRepository.findByPosLoginId(request.getPosLoginId());

        if (posOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인 실패"));
        }

        Pos pos = posOptional.get();
        BusinessUser businessUser = pos.getBusinessUser();

        // ✅ 비밀번호 암호화된 것과 비교
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
