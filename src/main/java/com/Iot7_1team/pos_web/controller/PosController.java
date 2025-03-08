package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.service.PosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pos")
public class PosController {
    private final PosService posService;

    public PosController(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/check-pos-login-id")
    public ResponseEntity<String> checkPosLoginId(@RequestParam String posLoginId) {
        boolean isAvailable = posService.isPosLoginIdAvailable(posLoginId);
        if (isAvailable) {
            return ResponseEntity.ok("POS 로그인 ID 사용 가능");
        } else {
            return ResponseEntity.badRequest().body("이미 사용 중인 POS 로그인 ID입니다.");
        }
    }
}

