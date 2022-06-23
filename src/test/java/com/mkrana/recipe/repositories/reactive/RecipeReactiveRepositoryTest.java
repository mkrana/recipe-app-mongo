package com.mkrana.recipe.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import com.mkrana.recipe.domain.Recipe;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version = 3.5.5")
class RecipeReactiveRepositoryTest {
	
	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;

	@BeforeEach
	void setUp() throws Exception {
		recipeReactiveRepository.deleteAll().block();
	}

	@Test
	void testRecipeSave() {
		Recipe recipe = new Recipe();
		recipe.setDescription("randomStuff");
		recipeReactiveRepository.save(recipe).block();
		Long count = recipeReactiveRepository.count().block();
		assertEquals(Long.valueOf(1L), count);
	}

}
