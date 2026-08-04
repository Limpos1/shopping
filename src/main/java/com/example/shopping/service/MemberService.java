package com.example.shopping.service;

import com.example.shopping.domain.Member;
import com.example.shopping.dto.MemberJoinForm;
import com.example.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	// 아이디 중복 확인
	public boolean isDuplicateUsername(String username){
		return memberRepository.findByUsername(username).isPresent();
	}
	
	// 회원 가입
	@Transactional
	public void join(MemberJoinForm joinForm){
		Member member = new Member(
				joinForm.getUsername(),
				passwordEncoder.encode(joinForm.getPassword()),
				joinForm.getName());
		
		memberRepository.save(member);
	
	}
	
	// 로그인 아이디로 회원정보 조회
	public Member findByUsername(String username){
		return memberRepository.findByUsername(username)
				.orElseThrow(()->new RuntimeException("존재하지 않는 사용자 입니다."));
	}
}
