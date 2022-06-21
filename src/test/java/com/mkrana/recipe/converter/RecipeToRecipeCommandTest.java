package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Difficulty;
import com.mkrana.recipe.domain.Ingredient;
import com.mkrana.recipe.domain.Notes;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.domain.UnitOfMeasure;

class RecipeToRecipeCommandTest {

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

	RecipeToRecipeCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new CategoryToCategoryCommand());

	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new Recipe()));
	}

	@Test
	void testConvert() {
		Recipe recipe = new Recipe();
		recipe.setId(ID_1);
		recipe.setDescription(DESCRIPTION);
		recipe.setDirections(DIRECTIONS);
		recipe.setPrepTime(PREPTIME);
		recipe.setCookTime(COOKTIME);
		recipe.setServings(SERVINGS);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setImage(IMAGE);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);

		Notes note = new Notes();
		note.setId(ID_1);
		note.setRecipeNotes(DESCRIPTION);
		recipe.setNote(note);

		Ingredient ingredient = new Ingredient();
		ingredient.setAmount(AMOUNT);
		ingredient.setId(ID_1);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setUnitOfMeasure(new UnitOfMeasure());
		recipe.getIngredients().add(ingredient);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setAmount(AMOUNT);
		ingredient2.setId(ID_1);
		ingredient2.setDescription(DESCRIPTION);
		ingredient2.setUnitOfMeasure(new UnitOfMeasure());
		recipe.getIngredients().add(ingredient2);

		RecipeCommand recipeCommand = converter.convert(recipe);

		// All Test

		assertEquals(recipe.getId(), recipeCommand.getId());
		assertEquals(recipe.getDescription(), recipeCommand.getDescription());
		assertEquals(recipe.getCookTime(), recipeCommand.getCookTime());
		assertEquals(recipe.getPrepTime(), recipeCommand.getPrepTime());
		assertEquals(recipe.getUrl(), recipeCommand.getUrl());
		assertEquals(recipe.getSource(), recipeCommand.getSource());
		assertEquals(recipe.getImage(), recipeCommand.getImage());
		assertEquals(recipe.getDirections(), recipeCommand.getDirections());
		assertEquals(recipe.getDifficulty(), recipeCommand.getDifficulty());
		assertEquals(recipe.getServings(), recipeCommand.getServings());
		assertEquals(recipe.getIngredients().size(), recipeCommand.getIngredients().size());
		assertEquals(recipe.getCategories().size(), recipeCommand.getCategories().size());

	}

}
