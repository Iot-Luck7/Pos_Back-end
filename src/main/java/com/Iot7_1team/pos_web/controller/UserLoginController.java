package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.UserLoginRequestDTO;
import com.Iot7_1team.pos_web.service.UserLoginService;
import com.Iot7_1team.pos_web.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pos")
@CrossOrigin(origins = "http://localhost:3000")
public class UserLoginController {

    private final UserLoginService userLoginService;
    private final JwtUtil jwtUtil;

    public UserLoginController(UserLoginService userLoginService, JwtUtil jwtUtil) {
        this.userLoginService = userLoginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO requestDTO) {
        if (requestDTO.getPosLoginId() == null || requestDTO.getPosPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "로그인 ID와 비밀번호를 입력하세요."));
        }

        Optional<Long> businessIdOpt = userLoginService.authenticate(requestDTO.getPosLoginId(), requestDTO.getPosPassword());

        if (businessIdOpt.isPresent()) {
            Long businessId = businessIdOpt.get();
            String token = jwtUtil.generateToken(requestDTO.getPosLoginId(), businessId); // ✅ businessId 포함
            return ResponseEntity.ok(Map.of("message", "로그인 성공!", "token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다."));
        }
    }
}
