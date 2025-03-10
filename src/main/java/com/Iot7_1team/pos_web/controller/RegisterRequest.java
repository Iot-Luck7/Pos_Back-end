package com.Iot7_1team.pos_web.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 등록 요청을 처리하는 DTO 클래스.
 * 클라이언트에서 전송하는 데이터를 한 번에 받을 수 있도록 함.
 */
@Getter @Setter
public class RegisterRequest {
    private BusinessDto business; // 비즈니스 정보
    private PosDto pos; // POS 정보
}

/**
 * 비즈니스 정보를 담는 DTO 클래스.
 * 클라이언트와 서버 간에 데이터를 주고받을 때 사용됨.
 */
@Getter @Setter
class BusinessDto {
    private String businessType; // 비즈니스 유형 (예: 카페, 음식점 등)
    private String businessName; // 비즈니스 이름
    private String sponsorshipYn; // 후원 여부 (Y/N)
}

/**
 * POS 정보를 담는 DTO 클래스.
 * POS 시스템에서 로그인 ID, 비밀번호, 위치 정보를 포함함.
 */
@Getter @Setter
class PosDto {
    private String posLoginId; // POS 로그인 ID
    private String posPassword; // POS 비밀번호
    private Double latitude; // POS의 위도
    private Double longitude; // POS의 경도
}

/**
 * DTO(Data Transfer Object)란?
 * - 데이터 전송을 위해 사용되는 객체.
 * - 클라이언트와 서버 간 데이터를 주고받을 때 엔터티 대신 사용.
 * - 데이터 보호 및 API 안정성을 유지하는 역할 수행.

 * DTO가 필요한 이유:
 * - 엔터티 직접 사용 시 보안 문제 발생 가능 (비밀번호 노출 등).
 * - 클라이언트 요청 구조와 엔터티 구조가 다를 수 있음.
 * - 필요하지 않은 필드를 제외하여 데이터 최적화 가능.
 
 * DTO의 동작 방식:
 * 1. 클라이언트가 JSON 데이터를 요청 본문에 포함하여 전송.
 * 2. 컨트롤러가 @RequestBody를 통해 DTO 객체로 변환.
 * 3. 변환된 DTO를 서비스 계층에서 엔터티로 변환하여 저장 또는 처리.
 * 4. 응답 시 DTO를 이용하여 클라이언트에 필요한 데이터만 반환.
 */
