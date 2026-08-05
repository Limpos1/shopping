package com.example.shopping.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
	@NotEmpty(message = "받는 분을 입력해 주세요.")
	private String receiverName;
	
	@NotEmpty(message = "주소를 입력해 주세요.")
	private String address;
	
	@NotEmpty(message = "연락처를 입력해 주세요.")
	private String phone;
}