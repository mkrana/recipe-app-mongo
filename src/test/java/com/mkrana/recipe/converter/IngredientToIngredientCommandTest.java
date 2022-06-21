package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.domain.Ingredient;
import com.mkrana.recipe.domain.UnitOfMeasure;

class IngredientToIngredientCommandTest {
	private final String ID = "1";
	private final String DESCRIPTION = "Bread";
	private final BigDecimal AMOUNT = new BigDecimal(2);
	private final String UOM = "Cup";

	IngredientToIngredientCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyParameter() {
		assertNotNull(converter.convert(new Ingredient()));
	}

	@Test
	void convert() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(ID);
		uom.setUom(UOM);
		Ingredient ingredient = new Ingredient(DESCRIPTION, AMOUNT, uom);
		ingredient.setId(ID);
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		assertNotNull(ingredient.getUnitOfMeasure());
		assertEquals(ingredientCommand.getId(), ingredient.getId());
		assertEquals(ingredientCommand.getDescription(), ingredient.getDescription());
		assertEquals(ingredientCommand.getAmount(), ingredient.getAmount());
	}

}
