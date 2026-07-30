package com.example.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(){
		// /resources/templates/index.html을 렌더링해서 변환
		return "index";
	}
}
