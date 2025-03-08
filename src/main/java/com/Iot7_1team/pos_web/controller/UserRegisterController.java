package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
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
            if (request.getBusiness() == null || request.getBusiness().getBusinessName() == null) {
                return ResponseEntity.badRequest().body("비즈니스 이름이 누락되었습니다.");
            }
            if (request.getPos() == null || request.getPos().getPosLoginId() == null) {
                return ResponseEntity.badRequest().body("POS 로그인 ID가 누락되었습니다.");
            }

            Business business = new Business();
            business.setBusinessType(request.getBusiness().getBusinessType());
            business.setBusinessName(request.getBusiness().getBusinessName());
            business.setSponsorshipYn(request.getBusiness().getSponsorshipYn());

            Pos pos = new Pos();
            pos.setPosLoginId(request.getPos().getPosLoginId());
            pos.setPosPassword(request.getPos().getPosPassword());
            pos.setLatitude(request.getPos().getLatitude());
            pos.setLongitude(request.getPos().getLongitude());

            System.out.println("Registering business: " + business.getBusinessName());
            System.out.println("Registering POS with Login ID: " + pos.getPosLoginId());

            userService.registerUser(business, pos);
            return ResponseEntity.ok("유저 등록 성공");
        } catch (Exception e) {
            System.err.println("유저 등록 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body("유저 등록 실패: " + e.getMessage());
        }
    }
}
