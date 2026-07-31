package com.example.shopping.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth
						// 인증 없이 접근할 수 있는 경로 등록
						.requestMatchers("/",
								"/css/**",
								"/js/**",
								"/error/**",
								"/products",
								"/join",
								"/login").permitAll())
				.formLogin(form->form
						.loginPage("/login")
						// 로그인 처리 경로 -> POST 방식의 요청을 Security가 대신 인증 처리
						.loginProcessingUrl("/login")
				// 로그인 성공 후 이동할 경로
				.defaultSuccessUrl("/products")
				.failureUrl("/login?error=true"))
				.logout(logout->logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/products")
						// 로그아웃 후 세션을 초기화
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
				);
		return http.build();
		
	}
}
