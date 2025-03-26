package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.MenuRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.Menu;
import com.Iot7_1team.pos_web.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // 이 클래스가 REST API의 컨트롤러임을 나타냄
@RequestMapping("/api/menu") // 모든 요청은 "/api/menu" 경로로 시작함
@CrossOrigin(origins = "http://localhost:3000") // 프론트엔드(React 등)에서의 CORS 허용 설정
public class MenuController {

    private final MenuService menuService;

    // 생성자를 통해 MenuService를 주입받음 (의존성 주입)
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 메뉴 등록 API
     * 클라이언트가 메뉴 정보를 JSON으로 보내면, 해당 사업자의 메뉴로 등록함
     *
     * @param requestDTO  등록할 메뉴 정보 (이름, 가격 등)
     * @param businessId  어떤 사업자(business)의 메뉴인지 식별하는 ID
     * @return 성공/실패 메시지 반환
     */
    @PostMapping("/register/{businessId}")
    public ResponseEntity<?> registerMenu(@RequestBody MenuRegisterRequestDTO requestDTO, @PathVariable Long businessId) {
        String result = menuService.registerMenu(requestDTO, businessId);
        if (result.equals("메뉴 등록 성공!")) {
            return ResponseEntity.ok(Map.of("message", result)); // 성공 시 message 반환
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", result)); // 실패 시 error 메시지 반환
        }
    }

    /**
     * 특정 POS 기기에 등록된 메뉴 목록 조회 API
     *
     * @param posId 메뉴를 조회할 POS 기기 ID
     * @return 메뉴 리스트 반환
     */
    @GetMapping("/list/{posId}")
    public ResponseEntity<?> getMenusByPos(@PathVariable Long posId) {
        List<Menu> menus = menuService.getMenusByPosId(posId);
        return ResponseEntity.ok(menus); // 메뉴 리스트를 JSON으로 반환
    }
}
