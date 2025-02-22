package com.Iot7_1team.pos_web.controller;

import com.Iot7_1team.pos_web.dto.PosBusinessDTO;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.service.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pos")
public class PosController {

    @Autowired
    private PosService posService;

    @PostMapping("/register")
    public ResponseEntity<?> registerBusinessAndPos(@RequestBody PosBusinessDTO posBusinessDTO) {
        try {
            Pos savedPos = posService.registerBusinessAndPos(
                    posBusinessDTO.getBusiness(),
                    posBusinessDTO.getPos()
            );
            return ResponseEntity.ok(savedPos);
        } catch (RuntimeException e) {
            // 중복된 이메일 예외 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
