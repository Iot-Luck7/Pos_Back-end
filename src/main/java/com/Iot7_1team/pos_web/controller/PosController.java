package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.service.PosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * POS 관련 API 컨트롤러
 * POS 시스템과 관련된 기능을 제공하는 REST 컨트롤러.
 */
@RestController
@RequestMapping("/api/pos")
public class PosController {
    private final PosService posService;

    /**
     * 생성자 주입을 통해 PosService 인스턴스를 주입받음.
     * @param posService - POS 관련 서비스 객체
     */
    public PosController(PosService posService) {
        this.posService = posService;
    }

    /**
     * POS 로그인 ID 중복 체크 API
     * 클라이언트가 입력한 POS 로그인 ID가 이미 사용 중인지 확인하는 엔드포인트.
     * @param posLoginId - 확인할 POS 로그인 ID (쿼리 파라미터로 전달됨)
     * @return 사용 가능 여부 메시지를 포함한 ResponseEntity 반환
     */
    @GetMapping("/check-pos-login-id")
    public ResponseEntity<String> checkPosLoginId(@RequestParam String posLoginId) {
        // POS 로그인 ID가 사용 가능한지 여부를 확인하고 응답을 반환
        return posService.isPosLoginIdAvailable(posLoginId)
                ? ResponseEntity.ok("POS 로그인 ID 사용 가능") // 사용 가능하면 200 OK 응답
                : ResponseEntity.badRequest().body("이미 사용 중인 POS 로그인 ID입니다."); // 중복이면 400 Bad Request 응답
    }
}
