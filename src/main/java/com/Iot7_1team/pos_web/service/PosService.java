package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PosService {
    private final PosRepository posRepository;

    public PosService(PosRepository posRepository) {
        this.posRepository = posRepository;
    }

    @Transactional
    public void savePos(Pos pos) {
        posRepository.save(pos);
    }
}
