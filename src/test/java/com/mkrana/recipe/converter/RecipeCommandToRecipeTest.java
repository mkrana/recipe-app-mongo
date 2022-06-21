package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.CategoryCommand;
import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.command.NotesCommand;
import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.Difficulty;
import com.mkrana.recipe.domain.Recipe;

class RecipeCommandToRecipeTest {

	private final String ID_1 = "1";
	private final String ID_2 = "2";
	private final String DESCRIPTION = "Recipe Description";
	private final Integer PREPTIME = 15;
	private final Integer COOKTIME = 30;
	private final String SOURCE = "SimplyRecipe";
	private final String URL = "http://www.simplyrecipe.com/make-abread";
	private final Byte[] IMAGE = { '2', '3' };
	private final String RECIPE_NOTES = "fancyNotes";
	private final Difficulty DIFFICULTY = Difficulty.EASY;
	private final Integer SERVINGS = 4;
	private final String DIRECTIONS = "Hello Random Directions";
	private final BigDecimal AMOUNT = BigDecimal.valueOf(2);

	RecipeCommandToRecipe converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new RecipeCommandToRecipe(
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				new CategoryCommandToCategory(), new NotesCommandToNotes());
	}

	@Test
	void testNull() {
		assertNull(converter.convert(null));

	}

	@Test
	void testEmpty() {
		assertNotNull(converter.convert(new RecipeCommand()));
	}

	@Test
	void testConvert() {
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setRecipeNotes(RECIPE_NOTES);

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ID_1);
		recipeCommand.setDescription(DESCRIPTION);
		recipeCommand.setCookTime(COOKTIME);
		recipeCommand.setPrepTime(PREPTIME);
		recipeCommand.setDifficulty(DIFFICULTY);
		recipeCommand.setNote(notesCommand);
		recipeCommand.setServings(SERVINGS);
		recipeCommand.setSource(SOURCE);
		recipeCommand.setUrl(URL);
		recipeCommand.setImage(IMAGE);
		recipeCommand.setDirections(DIRECTIONS);

		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_1);
		categoryCommand.setDescription(DESCRIPTION);

		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand2.setId(ID_2);
		categoryCommand2.setDescription(DESCRIPTION);

		recipeCommand.getCategories().add(categoryCommand);
		recipeCommand.getCategories().add(categoryCommand2);

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID_1);
		ingredientCommand.setAmount(AMOUNT);
		ingredientCommand.setDescription(DESCRIPTION);
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

		IngredientCommand ingredientCommand2 = new IngredientCommand();
		ingredientCommand2.setId(ID_2);
		ingredientCommand2.setAmount(AMOUNT);
		ingredientCommand2.setDescription(DESCRIPTION);
		ingredientCommand2.setUnitOfMeasure(new UnitOfMeasureCommand());

		recipeCommand.getIngredients().add(ingredientCommand);
		recipeCommand.getIngredients().add(ingredientCommand2);

		Recipe recipe = converter.convert(recipeCommand);

		assertEquals(recipe.getId(), recipeCommand.getId());
		assertEquals(recipe.getDescription(), recipeCommand.getDescription());
		assertEquals(recipe.getCookTime(), recipeCommand.getCookTime());
		assertEquals(recipe.getPrepTime(), recipeCommand.getPrepTime());
		assertEquals(recipe.getUrl(), recipeCommand.getUrl());
		assertEquals(recipe.getSource(), recipeCommand.getSource());
		assertEquals(recipe.getImage(), recipeCommand.getImage());
		assertEquals(recipe.getDifficulty(), recipeCommand.getDifficulty());
		assertEquals(recipe.getDirections(), recipeCommand.getDirections());
		assertEquals(recipe.getServings(), recipeCommand.getServings());
		assertEquals(recipe.getIngredients().size(), recipeCommand.getIngredients().size());
		assertEquals(recipe.getCategories().size(), recipeCommand.getCategories().size());

	}

}
