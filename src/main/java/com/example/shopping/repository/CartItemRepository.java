package com.example.shopping.repository;

import com.example.shopping.domain.CartItem;
import com.example.shopping.domain.Member;
import com.example.shopping.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByMember(Member member);
	Optional<CartItem> findByMemberAndProduct(Member member, Product product);
	
	
}