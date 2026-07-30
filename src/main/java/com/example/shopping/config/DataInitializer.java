package com.example.shopping.config;

import com.example.shopping.domain.Product;
import com.example.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
	초기 데이터 입력
	데이터베이스를 초기화 하면 기존 데이터가 모두 사라지기 때문에
	실행할 때 마다 테스트용 데이터를 자동으로 넣어준다.
	CommandLineRunner: 스프링부트가 실행되면 run() 메서드를 한 번 실행한다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
	private final ProductRepository productRepository;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Data 실행");
		// 상품 정보 등록
		List<Product> products = new ArrayList<>();
		products.add(new Product("키보드",90000,10,"기계식 키보드",null));
		products.add(new Product("마우스",32000,25,"무선 마우스",null));
		products.add(new Product("모니터",249000,5,"QHD 해상도 27인치 모니터",null));
		
		// product 테이블에 저장
		productRepository.saveAll(products);
		
		// 회원정보 등록
		
	}
}
