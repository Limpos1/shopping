package com.example.shopping.controller;

import com.example.shopping.config.CustomUserDetails;
import com.example.shopping.domain.CartItem;
import com.example.shopping.dto.OrderForm;
import com.example.shopping.service.CartService;
import com.example.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
@Controller
public class OrderController {
	private final OrderService orderService;
	private final CartService cartService;
	
	@GetMapping("/new")
	public String orderForm(@ModelAttribute("orderForm")OrderForm orderForm,
							Model model,
							@AuthenticationPrincipal CustomUserDetails userDetails){
		List<CartItem> cartItems = cartService.getCartItems(userDetails.getUsername());
		if(cartItems.isEmpty()){
			throw new RuntimeException("장바구니가 비어있습니다.");
		}
		
		model.addAttribute("cartItems",cartItems);
		model.addAttribute("totalPrice",cartService.getTotalPrice(cartItems);
		
		return "order/form";
	}
}
