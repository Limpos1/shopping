package com.example.shopping.repository;

import com.example.shopping.domain.Member;
import com.example.shopping.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
	List<Order> findByMemberOrderByIdDesc(Member member);
}