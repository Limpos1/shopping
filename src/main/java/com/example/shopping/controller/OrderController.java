package com.example.shopping.controller;

import com.example.shopping.config.CustomUserDetails;
import com.example.shopping.domain.CartItem;
import com.example.shopping.domain.Order;
import com.example.shopping.dto.OrderForm;
import com.example.shopping.service.CartService;
import com.example.shopping.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
@Controller
public class OrderController {
	private final OrderService orderService;
	private final CartService cartService;
	
	// 주문서 작성 페이지
	@GetMapping("/new")
	public String orderForm(@ModelAttribute("orderForm") OrderForm orderForm,
							Model model,
							@AuthenticationPrincipal CustomUserDetails userDetails){
		List<CartItem> cartItems = cartService.getCartItems(userDetails.getUsername());
		if(cartItems.isEmpty()){
			return "redirect:/cart";
		}
		
		model.addAttribute("cartItems",cartItems);
		model.addAttribute("totalPrice",cartService.getTotalPrice(cartItems));
		
		return "order/form";
	}
	
	// 주문서 제출 -> 주문 생성
	@PostMapping
	public String order(@AuthenticationPrincipal CustomUserDetails userDetails,
						@Valid @ModelAttribute("orderForm") OrderForm orderForm,
						BindingResult result,
						Model model){
		if(result.hasErrors()){
			return orderFormWithCart(userDetails, model, null);
		}
		
		try{
			Long orderId = orderService.placeOrder(userDetails.getUsername(), orderForm);
			return "redirect:/orders/" + orderId;
		} catch (IllegalArgumentException | IllegalStateException e){
			return orderFormWithCart(userDetails, model, e.getMessage());
		}
	}
	
	// 주문 내역 목록
	@GetMapping
	public String list(@AuthenticationPrincipal CustomUserDetails userDetails, Model model){
		List<Order> orders = orderService.getOrders(userDetails.getUsername());
		model.addAttribute("orders", orders);
		return "order/list";
	}
	
	// 주문 상세
	@GetMapping("/{id}")
	public String detail(@AuthenticationPrincipal CustomUserDetails userDetails,
						 @PathVariable Long id, Model model){
		Order order = orderService.getOrder(userDetails.getUsername(), id);
		model.addAttribute("order", order);
		return "order/detail";
	}
	
	// 주문 취소
	@PostMapping("/{id}/cancel")
	public String cancel(@AuthenticationPrincipal CustomUserDetails userDetails,
						 @PathVariable Long id){
		orderService.cancelOrder(userDetails.getUsername(), id);
		return "redirect:/orders/" + id;
	}
	
	// 주문서 화면을 (에러 유무와 상관없이) 다시 그릴 때 장바구니 정보를 채워준다.
	private String orderFormWithCart(CustomUserDetails userDetails, Model model, String errorMessage){
		List<CartItem> cartItems = cartService.getCartItems(userDetails.getUsername());
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", cartService.getTotalPrice(cartItems));
		if(errorMessage != null){
			model.addAttribute("errorMessage", errorMessage);
		}
		return "order/form";
	}
}