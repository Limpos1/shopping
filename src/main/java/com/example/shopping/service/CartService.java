package com.example.shopping.service;

import com.example.shopping.domain.CartItem;
import com.example.shopping.domain.Member;
import com.example.shopping.domain.Product;
import com.example.shopping.repository.CartItemRepository;
import com.example.shopping.repository.MemberRepository;
import com.example.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
	private final CartItemRepository cartItemRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	
	// 장바구니 목록 조회
	public List<CartItem> getCartItems(String username){
		Member member = getMember(username);
		return cartItemRepository.findByMember(member);
	}
	
	// 장바구니 담기 (이미 담긴 상품이면 수량만 증가)
	@Transactional
	public void addCart(String username, Long productId, int quantity){
		Member member = getMember(username);
		Product product = productRepository.findById(productId)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다. id="+productId));
		
		cartItemRepository.findByMemberAndProduct(member, product)
				.ifPresentOrElse(
						cartItem -> cartItem.addQuantity(quantity),
						() -> cartItemRepository.save(new CartItem(member, product, quantity))
				);
	}
	
	// 수량 변경
	@Transactional
	public void changeQuantity(String username, Long cartItemId, int quantity){
		CartItem cartItem = getOwnedCartItem(username, cartItemId);
		cartItem.changeQuantity(quantity);
	}
	
	// 장바구니에서 삭제
	@Transactional
	public void removeItem(String username, Long cartItemId){
		CartItem cartItem = getOwnedCartItem(username, cartItemId);
		cartItemRepository.delete(cartItem);
	}
	
	private Member getMember(String username){
		return memberRepository.findByUsername(username)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
	}
	
	// 요청한 회원의 장바구니 항목이 맞는지 확인 후 반환 (다른 사람 장바구니 조작 방지)
	private CartItem getOwnedCartItem(String username, Long cartItemId){
		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 장바구니 항목입니다."));
		if(!cartItem.getMember().getUsername().equals(username)){
			throw new IllegalArgumentException("본인의 장바구니만 수정할 수 있습니다.");
		}
		return cartItem;
	}
}