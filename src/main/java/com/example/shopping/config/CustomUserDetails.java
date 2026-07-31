package com.example.shopping.config;

/*
	Spring Security가 인증에 사용하는 사용자 정보
	우리가 만든 Member를 감싸서 UserDetails 타입에 맞춘다.
	컨트롤러에서 @AuthenticationPrincipal CustomUserDetails로 꺼낼 수 있다.
 */

import com.example.shopping.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	private final Member member;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(member.getRole().name()));
	}
	
	@Override
	public @Nullable String getPassword() {
		return member.getPassword();
	}
	
	@Override
	public String getUsername() {
		return member.getUsername();
	}
}
