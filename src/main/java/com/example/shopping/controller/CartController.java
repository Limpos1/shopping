package com.example.shopping.controller;

import com.example.shopping.config.CustomUserDetails;
import com.example.shopping.domain.CartItem;
import com.example.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;
	
	// 장바구니 목록 페이지(/cart)
	@GetMapping
	public String list(@AuthenticationPrincipal CustomUserDetails userDetails, Model model){
		List<CartItem> cartItems = cartService.getCartItems(userDetails.getUsername());
		int totalPrice = cartItems.stream().mapToInt(CartItem::getTotalPrice).sum();
		
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", totalPrice);
		return "cart/list";
	}
	
	// 장바구니 담기 (상품 상세 페이지에서 호출)
	@PostMapping("/add")
	public String add(@AuthenticationPrincipal CustomUserDetails userDetails,
					  @RequestParam Long productId,
					  @RequestParam(defaultValue = "1") int quantity){
		cartService.addCart(userDetails.getUsername(), productId, quantity);
		return "redirect:/cart";
	}
	
	// 수량 변경
	@PostMapping("/{id}/update")
	public String update(@AuthenticationPrincipal CustomUserDetails userDetails,
						 @PathVariable Long id,
						 @RequestParam int quantity){
		cartService.changeQuantity(userDetails.getUsername(), id, quantity);
		return "redirect:/cart";
	}
	
	// 삭제
	@PostMapping("/{id}/delete")
	public String delete(@AuthenticationPrincipal CustomUserDetails userDetails,
						 @PathVariable Long id){
		cartService.removeItem(userDetails.getUsername(), id);
		return "redirect:/cart";
	}
}