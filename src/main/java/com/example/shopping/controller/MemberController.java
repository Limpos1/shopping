package com.example.shopping.controller;

import com.example.shopping.dto.MemberJoinForm;
import com.example.shopping.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
	private MemberService memberService;
	
	@GetMapping("/login")
	public String loginForm(){
		return "member/login";
	}
	
	// 회원가입 페이지 이동
	@GetMapping("/join")
	public String joinForm(Model model){
		MemberJoinForm joinForm = new MemberJoinForm();
		model.addAttribute("joinForm",joinForm);
		return "member/join";
	}
	
	// 회원가입 처리
	// BindingResult -> @Valid에서 검증 후 발생한 예외를 받아서 저장
	// 이게 있으면 검증 예외가 발생 해도 예외를 던지지 않고 메서드 내의 코드를 실행한다.
	@PostMapping("/join")
	public String join(@Valid @ModelAttribute("joinForm") MemberJoinForm joinForm,
					   BindingResult result ,Model model
					   ) {
		// 검증에 실패한 결과가 있는지 확인
		if(result.hasErrors()){
			return "member/join";
		}
		
		// 아이디 중복 확인
		if(memberService.isDuplicateUsername(joinForm.getUsername())){
			// 아이디 중복 오류를 BindingResult에 직접 담아서 다른 오류 검증값과 같이 보낸다.
			result.rejectValue("username","duplicate","이미 사용중인 아이디 입니다.");
			return "member/join";
		}
		
		memberService.join(joinForm);
		return "redirect:/products";
	}
	
}
