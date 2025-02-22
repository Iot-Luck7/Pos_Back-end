package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.Pos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PosRepository extends JpaRepository<Pos, Long> {
    @Query("SELECT COUNT(p) > 0 FROM Pos p WHERE LOWER(p.userEmail) = LOWER(:userEmail)")
    boolean existsByUserEmail(@Param("userEmail") String userEmail);
}


