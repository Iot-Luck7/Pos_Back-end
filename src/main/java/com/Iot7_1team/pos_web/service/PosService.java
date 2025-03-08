package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PosService {
    private final PosRepository posRepository;

    public PosService(PosRepository posRepository) {
        this.posRepository = posRepository;
    }

    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public boolean isPosLoginIdAvailable(String posLoginId) {
        Optional<Pos> existingPos = posRepository.findByPosLoginId(posLoginId);
        return existingPos.isEmpty(); // 사용 가능하면 true, 중복이면 false
    }

    public Pos savePos(Pos pos) {
        return posRepository.save(pos);
    }
}
