package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.UserRegisterRequestDTO;
import com.Iot7_1team.pos_web.service.UserRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController // 이 클래스가 REST API 컨트롤러임을 나타냄
@RequestMapping("/api/pos") // "/api/pos" 경로로 시작하는 요청 처리
@CrossOrigin(origins = "http://localhost:3000") // React 앱에서의 CORS 허용
public class UserRegisterController {

    private final UserRegisterService userRegisterService; // 회원가입 로직을 처리할 서비스 클래스

    // 생성자를 통해 UserRegisterService 주입
    public UserRegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    /**
     * 유저 등록(회원가입) API
     *
     * @param requestDTO 클라이언트로부터 받은 회원가입 정보 (JSON 형식)
     * @return 성공 시 메시지 / 실패 시 에러 메시지
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDTO requestDTO) {
        try {
            // 서비스 호출하여 회원가입 처리
            userRegisterService.registerUser(requestDTO);
            // 성공 시 메시지 반환
            return ResponseEntity.ok().body(Map.of("message", "유저 등록 성공!"));
        } catch (IllegalArgumentException e) {
            // 유효성 검사 실패 등으로 발생한 예외 처리
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseEntity.badRequest().body(Map.of("error", "유저 등록 실패: " + e.getMessage()));
        }
    }
}
