package com.Iot7_1team.pos_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 설정을 정의하는 클래스.
 * CSRF 보호, CORS 설정 및 HTTP 요청에 대한 보안 규칙을 지정함.
 */
@Configuration
public class SecurityConfig {

    /**
     * Spring Security의 필터 체인을 정의하는 Bean.
     * @param http HttpSecurity 객체로 보안 설정을 정의함.
     * @return 설정된 SecurityFilterChain 객체 반환.
     * @throws Exception 예외 발생 시 던짐.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 기능을 비활성화. (REST API에서 CSRF 보호가 불필요한 경우가 많음)
                .csrf(AbstractHttpConfigurer::disable)

                // 기본적인 CORS 설정을 활성화. (별도의 CORS 설정 적용 가능)
                .cors(withDefaults())

                // HTTP 요청에 대한 인증 및 권한 부여 규칙을 정의함
                .authorizeHttpRequests(authorize -> authorize
                        // "/api/pos/**" 경로로 들어오는 요청은 인증 없이 허용
                        .requestMatchers("/api/pos/**").permitAll()
                        // "/api/menu/**" 경로로 들어오는 요청도 인증 없이 허용
                        .requestMatchers("/api/menu/**").permitAll()
                        // 그 외 모든 요청은 인증이 필요함
                        .anyRequest().authenticated()
                )

                // HTTP Basic 인증을 활성화 (단순한 보안 인증 방식, 개발 및 테스트 용도로 사용 가능)
                .httpBasic(withDefaults());

        return http.build(); // 설정된 HttpSecurity를 기반으로 SecurityFilterChain 객체를 생성하여 반환
    }

    /**
     * CORS(Cross-Origin Resource Sharing) 설정을 정의하는 Bean.
     * 프론트엔드와 백엔드가 다른 도메인에서 실행될 때 필요한 설정.
     * @return 설정된 CorsConfigurationSource 객체 반환.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 도메인(Origin) 패턴을 지정 (로컬 개발 환경에서는 http://localhost:3000 허용)
        configuration.addAllowedOriginPattern("http://localhost:3000");

        // 모든 HTTP 메서드(GET, POST, PUT, DELETE 등)를 허용
        configuration.addAllowedMethod("*");

        // 모든 HTTP 헤더를 허용
        configuration.addAllowedHeader("*");

        // 인증 정보(쿠키, Authorization 헤더 등)를 포함한 요청을 허용
        configuration.setAllowCredentials(true);

        // 특정 URL 패턴에 대해 CORS 설정을 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source; // 설정된 CORS 정책을 반환
    }
}
