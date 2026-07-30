package com.example.shopping.repository;

import com.example.shopping.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// findById, findAll, save, saveAll, delete, deleteById ...
	
	// 키워드 검색 (쿼리 메서드)
	List<Product> findByNameContainingOrderByIdDesc(String keyword);
	
	// 키워드 검색 (JPQL -> 데이터베이스 테이블이 아니고 엔티티 객체를 대상으로 한다.)
	@Query("select p from Product p where p.name like %:keyword% order by p.id desc")
	List<Product> findProducts(String keyword);
	
	
}
