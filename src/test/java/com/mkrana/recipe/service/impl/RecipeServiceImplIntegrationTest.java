package com.mkrana.recipe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.converter.RecipeToRecipeCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.RecipeRepository;
import com.mkrana.recipe.service.RecipeService;

@SpringBootTest
class RecipeServiceImplIntegrationTest {

	private final String DESCRIPTION = "NEW DESCRIPTION";

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private RecipeToRecipeCommand recipeToRecipeCommand;

	@Autowired
	RecipeService recipeService;

	@Transactional
	@Test
	void testSaveRecipeCommand() {
		// When
		Iterator<Recipe> recipeIterator = recipeRepository.findAll().iterator();
		Recipe savedRecipe = recipeIterator.next();

		// Setup Data for change
		RecipeCommand recipeCommand = recipeToRecipeCommand.convert(savedRecipe);
		recipeCommand.setDescription(DESCRIPTION);

		// Test
		RecipeCommand savedRecipeCommand = recipeService.saveRecipe(recipeCommand);
		assertEquals(savedRecipeCommand.getDescription(), savedRecipe.getDescription());
		assertEquals(savedRecipeCommand.getIngredients().size(), savedRecipe.getIngredients().size());
		assertEquals(savedRecipeCommand.getCategories().size(), savedRecipe.getCategories().size());

	}

}
