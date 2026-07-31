package com.example.shopping.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 회원가입 화면
@Getter
@Setter
public class MemberJoinForm {
	// 필드 유효성 검증
	@NotEmpty(message = "아이디는 비어있을 수 없다.")	// null이나 빈 값이 들어올 수 없다.
	@Size(min = 3, max = 20)
	private String username;
	
	@NotEmpty
	@Size(min = 4, max = 20)
	private String password;
	
	@NotEmpty
	private String name;
	
}
