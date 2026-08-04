package com.example.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	private int quantity;
	
	public CartItem(Member member, Product product, int quantity){
		this.member = member;
		this.product = product;
		this.quantity = quantity;
	}
	
	// 상품 단가 * 수량
	public int getTotalPrice(){
		return product.getPrice() * quantity;
	}
	
	public void addQuantity(int quantity){
		this.quantity += quantity;
	}
	
	public void changeQuantity(int quantity){
		this.quantity = quantity;
	}
}