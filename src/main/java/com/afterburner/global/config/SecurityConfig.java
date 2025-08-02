package com.afterburner.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-flutter}")
    private String allowedFlutter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/v1/users/firebase-login")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                // 헤더 설정
                .headers(headers -> headers
                        // X-Frame-Options 헤더를 DENY로 설정하여 클릭재킹 공격 방지
                        .frameOptions(frameOptions -> frameOptions.deny())
                        // HSTS 설정
                        .httpStrictTransportSecurity(hstsConfig -> hstsConfig
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true))
                        // 커스텀 헤더
                        .addHeaderWriter((request, response) -> {
                            response.setHeader("Cross-Origin-Opener-Policy", "same-origin-allow-popups");
                            response.setHeader("Cross-Origin-Embedder-Policy", "unsafe-none");
                        })
                )
                // Authorization 규칙 설정
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern(allowedOrigins);
        config.addAllowedOriginPattern(allowedFlutter);
        config.addAllowedOriginPattern("https://*.firebaseapp.com");
        config.addAllowedOriginPattern("https://*.googleapis.com");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("X-XSRF-TOKEN");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setExposedHeaders(List.of("X-XSRF-TOKEN"));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
