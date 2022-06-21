package com.mkrana.recipe.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mkrana.recipe.domain.Category;

@DataJpaTest
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void findByDescriptionTest() {
		Optional<Category> category = categoryRepository.findByDescription("American");
		assertEquals("American", category.get().getDescription());
	}

}
