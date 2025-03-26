package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.PosLoginRequest;
import com.Iot7_1team.pos_web.service.UserLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 이 클래스가 REST API 컨트롤러임을 나타냄 (JSON 기반 응답 처리)
@RequestMapping("/api/pos") // "/api/pos" 경로로 들어오는 요청을 이 컨트롤러에서 처리
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정 - React 앱(포트 3000)에서 요청 허용
public class UserLoginController {

    private final UserLoginService userLoginService; // 로그인 비즈니스 로직을 처리할 서비스

    // 생성자를 통한 의존성 주입
    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    /**
     * POS 로그인 요청 처리
     *
     * @param request 로그인 요청 데이터 (ID, 비밀번호 포함)
     * @return 로그인 성공 시 POS ID, 사업자 ID, 사업자 타입 등의 정보를 반환
     */
    @PostMapping("/login") // POST 요청 "/api/pos/login" 처리
    public ResponseEntity<?> login(@RequestBody PosLoginRequest request) {
        return userLoginService.login(request); // 로그인 로직은 서비스에서 처리
    }
}
