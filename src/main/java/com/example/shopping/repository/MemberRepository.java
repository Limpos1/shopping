package com.example.shopping.repository;

import com.example.shopping.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	// 아이디 중복 확인(쿼리 메서드)
	Optional<Member> findByUsername(String username);
}
