package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.RegisterRequest;
import com.Iot7_1team.pos_web.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pos")
public class UserRegisterController {
    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("유저 등록 성공!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("유저 등록 실패: " + e.getMessage());
        }
    }
}

