package com.afterburner.global.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String jwtToken = request.getHeader("Authorization"); // JWT 토큰 추출
		// JWT 검증 및 인증 처리 로직 추가

		filterChain.doFilter(request, response); // 다음 필터로 요청 전달
	}
}
