package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.MenuRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.Menu;
import com.Iot7_1team.pos_web.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/register/{businessId}")
    public ResponseEntity<?> registerMenu(@RequestBody MenuRegisterRequestDTO requestDTO, @PathVariable Long businessId) {
        String result = menuService.registerMenu(requestDTO, businessId);
        if (result.equals("메뉴 등록 성공!")) {
            return ResponseEntity.ok(Map.of("message", result));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", result));
        }
    }
    @GetMapping("/list/{posId}")
    public ResponseEntity<?> getMenusByPos(@PathVariable Long posId) {
        List<Menu> menus = menuService.getMenusByPosId(posId);
        return ResponseEntity.ok(menus);
    }
}
