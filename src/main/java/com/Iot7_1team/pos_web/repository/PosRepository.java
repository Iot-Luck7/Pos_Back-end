package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PosRepository extends JpaRepository<Pos, Long> {
    Optional<Pos> findByPosLoginId(String posLoginId);
}

