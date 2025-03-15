package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.MenuRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.BusinessUser;
import com.Iot7_1team.pos_web.model.Menu;
import com.Iot7_1team.pos_web.model.MenuPos;
import com.Iot7_1team.pos_web.repository.BusinessUserRepository;
import com.Iot7_1team.pos_web.repository.MenuRepository;
import com.Iot7_1team.pos_web.repository.MenuPosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final BusinessUserRepository businessUserRepository;
    private final MenuRepository menuRepository;
    private final MenuPosRepository menuPosRepository;

    public MenuService(BusinessUserRepository businessUserRepository, MenuRepository menuRepository, MenuPosRepository menuPosRepository) {
        this.businessUserRepository = businessUserRepository;
        this.menuRepository = menuRepository;
        this.menuPosRepository = menuPosRepository;
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

        // 메뉴 등록
        Menu newMenu = Menu.builder()
                .menuName(requestDTO.getMenuName())
                .category(requestDTO.getCategory())
                .price((double) requestDTO.getPrice())  // 🔹 Double 타입 맞춰야 함
                .calorie((double) requestDTO.getCalorie())
                .ingredients(requestDTO.getIngredients())
                .dietYn(requestDTO.isDietYn() ? "Y" : "N")
                .businessId(businessId)
                .regDate(LocalDateTime.now())
                .build();

        menuRepository.save(newMenu);

        // ✅ 본점이 등록한 경우, 가맹점에도 자동 추가
        if (businessUser.getBusinessType().equals("본점")) {
            List<BusinessUser> branches = businessUserRepository.findByBusinessType("가맹점");

            for (BusinessUser branch : branches) {
                Menu branchMenu = new Menu(newMenu, branch.getBusinessId()); // 🔹 복사 생성자 사용
                menuRepository.save(branchMenu);
            }
        }

        return "메뉴 등록 성공!";
    }

}

