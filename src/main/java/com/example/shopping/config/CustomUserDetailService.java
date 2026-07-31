package com.example.shopping.config;

import com.example.shopping.domain.Member;
import com.example.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
	로그인 페이지에서 폼데이터 전송된 username을 파라미터로 받는다.
	시큐리티 필터 체인에서 UserDetailsService의 loadUserByUsername 메서드를 호출한다.
	username에 해당하는 회원 정보를 찾아서 UserDetails 타입의 객체로 리턴한다.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("존재하지 않는 사용자 입니다."));
		return new CustomUserDetails(member);
	}
}
