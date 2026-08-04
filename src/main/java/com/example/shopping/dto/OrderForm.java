package com.example.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
	private String receiverName;
	private String address;
	private String phone;
}
