package com.example.shopping.controller;

import com.example.shopping.domain.Product;
import com.example.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products") // 이 컨트롤러의 핸들러 메서드에서 공통 경로로 사용할 값을 세팅한다.
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	// 상품 목록 페이지 이동(/products)
	@GetMapping
	public String list(@RequestParam(required = false,name = "keyword") String searchKeyword, Model model){
		List<Product> products = productService.getList(searchKeyword);
		model.addAttribute("products",products);
		// 검색 키워드 저장
		model.addAttribute("keyword",searchKeyword);
		return "product/list";
	}
	
	// 상품 상세 페이지 이동(/products/{id})
	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model){
		Product product = productService.getProduct(id);
		model.addAttribute("product",product);
		return "product/detail";
	}
}