package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.PosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * POS 관리 서비스
 */
@Service
public class PosService {
    private final PosRepository posRepository;

    public PosService(PosRepository posRepository) {
        this.posRepository = posRepository;
    }

    /**
     * POS 로그인 ID 사용 가능 여부 확인 (읽기 전용 트랜잭션)
     * @param posLoginId - 확인할 로그인 ID
     * @return 사용 가능 여부 반환
     */
    @Transactional(readOnly = true)
    public boolean isPosLoginIdAvailable(String posLoginId) {
        return posRepository.findByPosLoginId(posLoginId).isEmpty();
    }

    /**
     * POS 정보 저장
     * @param pos - 저장할 POS 객체
     * @return 저장된 POS 객체 반환
     */
    public Pos savePos(Pos pos) {
        return posRepository.save(pos);
    }
}