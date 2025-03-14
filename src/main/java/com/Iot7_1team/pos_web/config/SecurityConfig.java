package com.Iot7_1team.pos_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                // CSRF 비활성화 (REST API에서는 불필요한 경우가 많음)
                .csrf(AbstractHttpConfigurer::disable)

                // CORS 설정 적용 (반드시 `corsConfigurationSource()`를 연결해야 함)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 인증 및 권한 부여 규칙 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/pos/**").permitAll() // 특정 엔드포인트 인증 없이 허용
                        .requestMatchers("/api/menu/**").permitAll()
                        .anyRequest().authenticated()
                )

                // HTTP Basic 인증 사용 (테스트용)
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 Origin (로컬 React 서버 포함)
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 모든 헤더 허용
        configuration.setAllowedHeaders(List.of("*"));

        // 인증 정보(쿠키, Authorization 헤더 등) 포함 허용
        configuration.setAllowCredentials(true);

        // 특정 URL 패턴에 대해 CORS 정책 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 인코더
    }
}
