package com.mkrana.recipe.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.mkrana.recipe.domain.Category;
import com.mkrana.recipe.repositories.reactive.CategoryReactiveRepository;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class CategoryRepositoryTest {

	@Autowired
	CategoryReactiveRepository categoryRepository;

	@BeforeEach
	void setUp() throws Exception {
		 
	}

	@Test
	void findByDescriptionTest() {
		Category category = categoryRepository.findByDescription("Banana").block();
		assertEquals("Banana", category.getDescription());
	}

}
