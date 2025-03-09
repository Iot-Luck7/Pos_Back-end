package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 등록 API 컨트롤러
 */
@RestController
@RequestMapping("/api/pos")
public class UserRegisterController {
    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 유저 등록 API
     * @param request - 등록 요청 정보 (비즈니스, POS 정보 포함)
     * @return 성공/실패 메시지 반환
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        if (request.getBusiness() == null || request.getBusiness().getBusinessName() == null) {
            return ResponseEntity.badRequest().body("비즈니스 이름이 누락되었습니다.");
        }
        if (request.getPos() == null || request.getPos().getPosLoginId() == null) {
            return ResponseEntity.badRequest().body("POS 로그인 ID가 누락되었습니다.");
        }

        try {
            // DTO → 엔터티 변환
            Business business = convertToEntity(request.getBusiness());
            Pos pos = convertToEntity(request.getPos());

            userService.registerUser(business, pos);
            return ResponseEntity.ok("유저 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("유저 등록 실패: " + e.getMessage());
        }
    }

    /**
     * BusinessDto -> Business 변환
     */
    private Business convertToEntity(BusinessDto dto) {
        Business business = new Business();
        business.setBusinessType(dto.getBusinessType());
        business.setBusinessName(dto.getBusinessName());
        business.setSponsorshipYn(dto.getSponsorshipYn());
        return business;
    }

    /**
     * PosDto -> Pos 변환
     */
    private Pos convertToEntity(PosDto dto) {
        Pos pos = new Pos();
        pos.setPosLoginId(dto.getPosLoginId());
        pos.setPosPassword(dto.getPosPassword());
        pos.setLatitude(dto.getLatitude());
        pos.setLongitude(dto.getLongitude());
        return pos;
    }
}
