package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.UnitOfMeasure;

class UnitOfMeasureToUnitOfMeasureCommandTest {

	private final String ID = "1";

	private static String UOM = "Cup";

	UnitOfMeasureToUnitOfMeasureCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));

	}

	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new UnitOfMeasure()));

	}

	@Test
	void testConvert() {
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(ID);
		unitOfMeasure.setUom(UOM);
		UnitOfMeasureCommand measureCommand = converter.convert(unitOfMeasure);
		assertEquals(unitOfMeasure.getId(), measureCommand.getId());
		assertEquals(unitOfMeasure.getUom(), measureCommand.getUom());

	}

}
