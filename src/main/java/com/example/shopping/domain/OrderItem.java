package com.example.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order; // 어떤 주문에 속하는지
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product product; //주문한 상품
	
	private int orderPrice;
	private int count;
	
	public OrderItem(Product product, int orderPrice, int count) {
		this.product = product;
		this.orderPrice = orderPrice;
		this.count = count;
	}
	
	// 합계 금액 계산
	public int getTotalPrice(){
		return orderPrice * count;
	}
}
