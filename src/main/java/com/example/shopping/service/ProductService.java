package com.example.shopping.service;

import com.example.shopping.domain.Product;
import com.example.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
// 클라이언트 -> 요청 -> 컨트롤러(Return이 웹페이지)
//			-> 요청2(API) -> Rest컨트롤러(리턴이 데이터 예:JSON)
public class ProductService {
	private final ProductRepository productRepository;
	
	// 상품 목록 조회
	public List<Product> getList(String keyword){
		// keyword가 없으면 전체 조회
		if(keyword == null || keyword.isBlank()){
			return productRepository.findAll();
		}
		// keyword가 있으면 Like 조회
		//return productRepository.findByNameContainingOrderByIdDesc(keyword);
		return productRepository.findProducts(keyword);
		
		
	}
	
	// 단일 상품 조회
	public Product getProduct(Long id){
		return productRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다. id="+id));
	}
	
	// 상품 등록
	
	// 상품 수정
	
	// 상품 삭제
	
}