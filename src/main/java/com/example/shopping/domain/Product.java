package com.example.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor // Entity 클래스는 기본 생성자가 반드시 필요
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	// 식별자(PK)
	
	@Column(length = 300)
	private String name;	// 상품명
	private int price;	// 가격
	private int stock;	// 재고 수량
	
	@Column(columnDefinition = "TEXT")
	private String description; // 상품 설명
	
	private String imageUrl; // 상품 이미지 주소
	private LocalDateTime createdAt;
	
	// 생성자
	public Product(String name,int price, int stock, String description, String imageUrl){
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.imageUrl = imageUrl;
		this.createdAt = LocalDateTime.now();
	}
	
	// 헬퍼 메서드 추가
	// 재고가 있는지 여부
	public boolean isSoldOut(){
		return stock <= 0;
	}
	
	// 재고 감소
	// 재고보다 많이 주문하면 예외를 던져서 주문 전체가 롤백 되도록 한다.
	// 재고를 다루는 로직을 엔티티 안에 두면 다른 서비스 로직에서 중복되지 않는다.
	public void removeStock(int quantity){
		if(stock < quantity){
			throw new IllegalArgumentException(name+"의 재고가 부족합니다.");
		}
		this.stock -= quantity;
	}
	
	// 재고 증가
	public void addStock(int quantity){
		if(quantity < 0){
			throw new IllegalArgumentException("음수입력은 안된다!");
		}
		this.stock += quantity;
	}
}
