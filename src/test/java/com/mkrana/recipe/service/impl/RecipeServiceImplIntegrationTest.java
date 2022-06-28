package com.mkrana.recipe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.converter.RecipeToRecipeCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.service.RecipeService;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class RecipeServiceImplIntegrationTest {

	private final String DESCRIPTION = "NEW DESCRIPTION";

	@Autowired
	private RecipeReactiveRepository recipeRepository;

	@Autowired
	private RecipeToRecipeCommand recipeToRecipeCommand;

	@Autowired
	RecipeService recipeService;

	@Test
	void testSaveRecipeCommand() {
		// When
		List<Recipe> recipeIterator = recipeRepository.findAll().collectList().block();
		Recipe savedRecipe = recipeIterator.get(0);

		// Setup Data for change
		RecipeCommand recipeCommand = recipeToRecipeCommand.convert(savedRecipe);
		recipeCommand.setDescription(DESCRIPTION);

		// Test
		RecipeCommand savedRecipeCommand = recipeService.saveRecipe(recipeCommand).block();
		assertEquals(savedRecipeCommand.getDescription(), recipeCommand.getDescription());
		assertEquals(savedRecipeCommand.getIngredients().size(), recipeCommand.getIngredients().size());
		assertEquals(savedRecipeCommand.getCategories().size(), recipeCommand.getCategories().size());

	}

}
