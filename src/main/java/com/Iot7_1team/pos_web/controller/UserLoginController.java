package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.PosLoginRequest;
import com.Iot7_1team.pos_web.model.BusinessUser;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController // 이 클래스가 REST API 컨트롤러임을 나타냄
@RequestMapping("/api/pos") // "/api/pos" 경로로 들어오는 요청 처리
@CrossOrigin(origins = "http://localhost:3000") // React 앱과 연동을 위한 CORS 허용 설정
public class UserLoginController {

    private final PosRepository posRepository; // POS 정보를 DB에서 조회하기 위한 리포지토리

    // 생성자를 통한 의존성 주입
    public UserLoginController(PosRepository posRepository) {
        this.posRepository = posRepository;
    }

    /**
     * POS 로그인 처리 API
     *
     * @param request 로그인 요청 정보 (POS 로그인 ID, 비밀번호)
     * @return 로그인 성공 시 POS ID, 사업자 ID, 사업자 타입 반환 / 실패 시 에러 메시지
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PosLoginRequest request) {
        // 로그인 ID로 POS 정보 조회
        Optional<Pos> posOptional = posRepository.findByPosLoginId(request.getPosLoginId());

        // 해당 ID의 POS가 존재하지 않으면 로그인 실패
        if (posOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인 실패"));
        }

        Pos pos = posOptional.get(); // POS 정보 꺼내기
        BusinessUser businessUser = pos.getBusinessUser(); // 해당 POS에 연결된 사업자 정보도 함께 가져오기

        // 입력한 비밀번호와 암호화된 비밀번호 비교
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getPosPassword(), pos.getPosPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "비밀번호가 일치하지 않습니다."));
        }

        // 로그인 성공 시 필요한 정보 반환
        return ResponseEntity.ok(Map.of(
                "message", "로그인 성공",
                "posId", pos.getPosId(), // POS ID
                "businessId", businessUser.getBusinessId(), // 사업자 ID
                "businessType", businessUser.getBusinessType() // 사업자 유형 (ex. 본점, 개인 등)
        ));
    }
}
