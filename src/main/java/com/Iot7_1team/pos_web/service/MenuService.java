package com.Iot7_1team.pos_web.service;

import com.Iot7_1team.pos_web.dto.MenuRegisterRequestDTO;
import com.Iot7_1team.pos_web.model.*;
import com.Iot7_1team.pos_web.repository.BusinessUserRepository;
import com.Iot7_1team.pos_web.repository.MenuPosRepository;
import com.Iot7_1team.pos_web.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // 이 클래스가 서비스 레이어의 컴포넌트임을 명시
public class MenuService {

    private final BusinessUserRepository businessUserRepository; // 사업자 정보 조회용 리포지토리
    private final MenuRepository menuRepository; // 메뉴 저장 및 조회용 리포지토리
    private final MenuPosRepository menuPosRepository;
    // 생성자 주입
    public MenuService(BusinessUserRepository businessUserRepository,
                       MenuRepository menuRepository,
                       MenuPosRepository menuPosRepository) {
        this.businessUserRepository = businessUserRepository;
        this.menuRepository = menuRepository;
        this.menuPosRepository = menuPosRepository;
    }

    /**
     * 메뉴 등록 서비스
     *
     * @param requestDTO 등록할 메뉴 정보
     * @param businessId 메뉴를 등록하려는 사업자 ID
     * @return 성공/실패 메시지
     */
    @Transactional // 트랜잭션 처리 (등록 중 예외 발생 시 롤백됨)
    public String registerMenu(MenuRegisterRequestDTO requestDTO, Long businessId) {
        // 사업자 존재 여부 확인
        Optional<BusinessUser> businessOptional = businessUserRepository.findByBusinessId(businessId);

        if (businessOptional.isEmpty()) {
            return "등록 실패: 사업자가 존재하지 않습니다.";
        }

        BusinessUser businessUser = businessOptional.get();

        // 본점 또는 개인 사업자인 경우에만 메뉴 등록 가능
        if (!businessUser.getBusinessType().equals("본점") && !businessUser.getBusinessType().equals("개인")) {
            return "등록 실패: 본점 또는 개인 사업자만 메뉴를 등록할 수 있습니다.";
        }

        // ✅ 메뉴 정보 생성 및 저장
        Menu newMenu = Menu.builder()
                .menuName(requestDTO.getMenuName())           // 메뉴 이름
                .category(requestDTO.getCategory())           // 카테고리
                .price(requestDTO.getPrice())                 // 가격
                .calorie(requestDTO.getCalorie())             // 칼로리
                .ingredients(requestDTO.getIngredients())     // 재료
                .dietYn(requestDTO.isDietYn() ? "Y" : "N")    // 다이어트 여부 (Y/N)
                .businessId(businessId)                       // 사업자 ID
                .regDate(LocalDateTime.now())
                .imageUrl(requestDTO.getImageUrl())           // 등록 일시
                .build();

        menuRepository.save(newMenu); // DB에 저장

        // ✅ 트리거로 MENU_POS 테이블 자동 처리됨
        return "메뉴 등록 성공!";
    }

    /**
     * 특정 POS ID에 연결된 메뉴 목록 조회
     *
     * @param posId POS 기기 ID
     * @return 해당 POS에 등록된 메뉴 리스트
     */
    public List<Menu> getMenusByPosId(Long posId) {
        return menuRepository.findMenusByPosId(posId);
    }

    @Transactional
    public String deleteMenu(Long menuId) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);

        if (menuOptional.isEmpty()) {
            return "삭제 실패: 해당 메뉴가 존재하지 않습니다.";
        }

        // ✅ 메뉴-포스 연결 테이블 먼저 삭제
        menuPosRepository.deleteByMenuId(menuId);

        // ✅ 그 후 메뉴 삭제
        menuRepository.deleteById(menuId);

        return "삭제 성공";
    }

    @Transactional
    public String updateMenu(Long menuId, MenuRegisterRequestDTO dto) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty()) {
            return "수정 실패: 메뉴가 존재하지 않습니다.";
        }

        Menu menu = optionalMenu.get();
        menu.setMenuName(dto.getMenuName());
        menu.setCategory(dto.getCategory());
        menu.setPrice(dto.getPrice());
        menu.setCalorie(dto.getCalorie());
        menu.setIngredients(dto.getIngredients());
        menu.setDietYn(dto.isDietYn() ? "Y" : "N");

        if (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty()) {
            menu.setImageUrl(dto.getImageUrl());
        }

        menuRepository.save(menu); // 실제로는 생략해도 JPA가 자동 처리함
        return "메뉴 수정 성공!";
    }
}
