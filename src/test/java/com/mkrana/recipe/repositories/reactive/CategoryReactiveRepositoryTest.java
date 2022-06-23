package com.mkrana.recipe.repositories.reactive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import com.mkrana.recipe.domain.Category;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class CategoryReactiveRepositoryTest {

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		categoryReactiveRepository.deleteAll().block();
	}

	@Test
	void test() {
		categoryReactiveRepository.save(Category.builder().description("Foo").build()).block();
		categoryReactiveRepository.save(Category.builder().description("Category 2").build()).block();
		Long countOfCategories = categoryReactiveRepository.count().block();
		assertEquals(2, countOfCategories);
	}

}
