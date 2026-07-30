package com.example.shopping.controller;

import com.example.shopping.dto.MemberJoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
	
	// 회원가입 페이지 이동
	@GetMapping("/join")
	public String joinForm(Model model){
		MemberJoinForm joinForm = new MemberJoinForm();
		model.addAttribute("joinForm",joinForm);
		return "member/join";
	}
	
	// 회원가입 처리
	@PostMapping("/join")
	public String join(@ModelAttribute("joinForm") MemberJoinForm joinForm, Model model) {
		return "redirect:/products";
	}
}
