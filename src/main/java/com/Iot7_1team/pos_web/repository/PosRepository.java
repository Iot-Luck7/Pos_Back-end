package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * POS 테이블과의 데이터 액세스를 담당하는 JPA Repository.
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 제공하며,
 * POS 로그인 ID의 중복 여부를 확인하는 메서드를 추가로 정의함.
 */
@Repository // Spring이 해당 클래스를 Repository로 인식하고, Bean으로 관리하도록 지정
public interface PosRepository extends JpaRepository<Pos, Long> {

    /**
     * 특정 POS_LOGIN_ID를 가진 POS 계정을 조회하는 메서드.
     * - POS 로그인 ID는 중복될 수 없으므로, 회원가입 시 중복 여부를 확인할 때 사용됨.
     * - Optional을 반환하여, 해당 조건을 만족하는 값이 없을 경우 Null이 아니라 비어있는 Optional 객체를 반환.
     *
     * @param posLoginId 조회할 POS 로그인 ID (이메일 형태일 가능성이 높음)
     * @return 동일한 POS_LOGIN_ID를 가진 Pos 객체를 Optional로 반환
     */
    Optional<Pos> findByPosLoginId(String posLoginId);
}
