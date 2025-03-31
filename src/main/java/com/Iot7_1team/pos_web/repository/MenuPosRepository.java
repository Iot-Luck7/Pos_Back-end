package com.Iot7_1team.pos_web.repository;

import com.Iot7_1team.pos_web.model.MenuPos;
import com.Iot7_1team.pos_web.model.MenuPosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MenuPosRepository extends JpaRepository<MenuPos, MenuPosId> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM MENU_POS WHERE MENU_ID = :menuId", nativeQuery = true)
    void deleteByMenuId(@Param("menuId") Long menuId);
}
