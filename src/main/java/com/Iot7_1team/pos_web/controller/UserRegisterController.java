package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.UserRegisterRequestDTO;
import com.Iot7_1team.pos_web.service.UserRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pos")
@CrossOrigin(origins = "http://localhost:3000")
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    public UserRegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDTO requestDTO) {
        try {
            userRegisterService.registerUser(requestDTO);
            return ResponseEntity.ok().body(Map.of("message", "유저 등록 성공!")); // ✅ JSON 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // JSON 에러 메시지
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "유저 등록 실패: " + e.getMessage()));
        }
    }
}
