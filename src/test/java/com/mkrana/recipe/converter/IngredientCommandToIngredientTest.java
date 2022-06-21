package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.Ingredient;

class IngredientCommandToIngredientTest {
	private final String ID = "1";
	private final String DESCRIPTION = "Bread";
	private final BigDecimal AMOUNT = new BigDecimal(2);
	private final String UOM = "Cup";

	IngredientCommandToIngredient converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Test
	void testNullParamater() {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	void testConvertWithUom() {
		UnitOfMeasureCommand measureCommand = new UnitOfMeasureCommand();
		measureCommand.setId(ID);
		measureCommand.setUom(UOM);
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID);
		ingredientCommand.setDescription(DESCRIPTION);
		ingredientCommand.setUnitOfMeasure(measureCommand);
		ingredientCommand.setAmount(AMOUNT);

		Ingredient ingredient = converter.convert(ingredientCommand);

		assertNotNull(ingredient.getUnitOfMeasure());
		assertEquals(ingredient.getId(), ingredientCommand.getId());
		assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
		assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
	}

	@Test
	void testConvertNullUom() {

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID);
		ingredientCommand.setDescription(DESCRIPTION);
		ingredientCommand.setUnitOfMeasure(null);
		ingredientCommand.setAmount(AMOUNT);

		Ingredient ingredient = converter.convert(ingredientCommand);

		assertNull(ingredient.getUnitOfMeasure());
		assertEquals(ingredient.getId(), ingredientCommand.getId());
		assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
		assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
	}

}
