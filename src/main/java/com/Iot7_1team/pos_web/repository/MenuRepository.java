package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(value = "SELECT m.* FROM MENU m JOIN MENU_POS mp ON m.MENU_ID = mp.MENU_ID WHERE mp.POS_ID = :posId", nativeQuery = true)
    List<Menu> findMenusByPosId(@Param("posId") Long posId);

}

