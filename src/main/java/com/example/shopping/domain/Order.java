package com.example.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// mysql에서는 order가 예약어이기때문에 테이블명을 orders 변경
@Table(name="orders")
@NoArgsConstructor
@Getter @Setter
@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; // 주문 상태
	
	@CreationTimestamp // Order 객체가 생성될 때 시간 값을 자동으로 넣어줌
	@CreatedDate // 날짜값을 자동으로 넣어준다.
	private LocalDateTime orderedAt; // 주문 시간
	
	private String receiverName;
	private String address;
	private String phone;
	
	// 생성자
	public Order(Member member,String receiverName,String address,String phone){
		this.member = member;
		this.receiverName = receiverName;
		this.address = address;
		this.phone = phone;
		this.status = OrderStatus.ORDERED;
	}
}
