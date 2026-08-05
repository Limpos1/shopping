package com.example.shopping.service;

import com.example.shopping.domain.CartItem;
import com.example.shopping.domain.Member;
import com.example.shopping.domain.Order;
import com.example.shopping.domain.OrderItem;
import com.example.shopping.domain.OrderStatus;
import com.example.shopping.domain.Product;
import com.example.shopping.dto.OrderForm;
import com.example.shopping.repository.CartItemRepository;
import com.example.shopping.repository.OrderItemRepository;
import com.example.shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartItemRepository cartItemRepository;
	private final MemberService memberService;
	
	// 주문 생성 (장바구니 -> 주문)
	@Transactional
	public Long placeOrder(String username, OrderForm orderForm){
		Member member = memberService.findByUsername(username);
		List<CartItem> cartItems = cartItemRepository.findByMember(member);
		if(cartItems.isEmpty()){
			throw new IllegalStateException("장바구니가 비어있습니다.");
		}
		
		Order order = new Order(member, orderForm.getReceiverName(), orderForm.getAddress(), orderForm.getPhone());
		
		for(CartItem cartItem : cartItems){
			Product product = cartItem.getProduct();
			// 재고보다 많이 주문하면 예외 발생(재고 차감)
			product.removeStock(cartItem.getQuantity());
			
			OrderItem orderItem = new OrderItem(product, product.getPrice(), cartItem.getQuantity());
			order.addOrderItem(orderItem);
		}
		
		orderRepository.save(order);
		
		// 주문 완료 후 장바구니 비우기
		cartItemRepository.deleteAll(cartItems);
		
		return order.getId();
	}
	
	// 주문 내역 목록 조회
	public List<Order> getOrders(String username){
		Member member = memberService.findByUsername(username);
		return orderRepository.findByMemberOrderByIdDesc(member);
	}
	
	// 주문 상세 조회 (본인 주문인지 확인)
	public Order getOrder(String username, Long orderId){
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 주문입니다."));
		if(!order.getMember().getUsername().equals(username)){
			throw new IllegalArgumentException("본인의 주문만 조회할 수 있습니다.");
		}
		return order;
	}
	
	// 주문 취소 (재고 복구)
	@Transactional
	public void cancelOrder(String username, Long orderId){
		Order order = getOrder(username, orderId);
		order.setStatus(OrderStatus.CANCELED);
		for(OrderItem orderItem : order.getOrderItems()){
			orderItem.getProduct().addStock(orderItem.getCount());
		}
	}
}