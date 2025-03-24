package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.MenuRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.*;
import com.Iot7_1team.pos_web.repository.BusinessUserRepository;
import com.Iot7_1team.pos_web.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final BusinessUserRepository businessUserRepository;
    private final MenuRepository menuRepository;

    public MenuService(BusinessUserRepository businessUserRepository, MenuRepository menuRepository) {
        this.businessUserRepository = businessUserRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public String registerMenu(MenuRegisterRequestDTO requestDTO, Long businessId) {
        Optional<BusinessUser> businessOptional = businessUserRepository.findByBusinessId(businessId);

        if (businessOptional.isEmpty()) {
            return "등록 실패: 사업자가 존재하지 않습니다.";
        }

        BusinessUser businessUser = businessOptional.get();
        if (!businessUser.getBusinessType().equals("본점") && !businessUser.getBusinessType().equals("개인")) {
            return "등록 실패: 본점 또는 개인 사업자만 메뉴를 등록할 수 있습니다.";
        }

        // ✅ 메뉴 저장
        Menu newMenu = Menu.builder()
                .menuName(requestDTO.getMenuName())
                .category(requestDTO.getCategory())
                .price(requestDTO.getPrice())
                .calorie(requestDTO.getCalorie())
                .ingredients(requestDTO.getIngredients())
                .dietYn(requestDTO.isDietYn() ? "Y" : "N")
                .businessId(businessId)
                .regDate(LocalDateTime.now())
                .build();

        menuRepository.save(newMenu);

        // ✅ 트리거가 자동으로 `MENU_POS` 데이터를 추가하므로 더 이상 처리할 필요 없음
        return "메뉴 등록 성공!";
    }

    public List<Menu> getMenusByPosId(Long posId) {
        return menuRepository.findMenusByPosId(posId);
    }
}
