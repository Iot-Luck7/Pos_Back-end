package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.model.Business;
import com.Iot7_1team.pos_web.model.Pos;
import com.Iot7_1team.pos_web.repository.BusinessRepository;
import com.Iot7_1team.pos_web.repository.PosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PosService {

    @Autowired
    private PosRepository posRepository;

    @Autowired
    private BusinessRepository businessRepository;

    public Pos registerBusinessAndPos(Business business, Pos pos) {
        // 이메일 공백 제거 및 소문자로 변환
        String cleanedEmail = pos.getUserEmail().trim().toLowerCase();
        pos.setUserEmail(cleanedEmail);

        // 중복 체크: user_email이 이미 존재하는지 확인
        if (posRepository.existsByUserEmail(cleanedEmail)) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + cleanedEmail);
        }

        // 1. Business 저장
        Business savedBusiness = businessRepository.save(business);

        // 2. Business와 POS 연관 설정
        pos.setBusiness(savedBusiness);

        // 3. POS 저장
        Pos savedPos = posRepository.save(pos);

        return savedPos;
    }
}


