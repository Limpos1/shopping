package com.example.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true,nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	// 생성자 정의
	public Member(String username, String password, String name){
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = Role.ROLE_USER;
	}
	
}
