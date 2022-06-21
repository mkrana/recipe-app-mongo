package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.UnitOfMeasure;

class UnitOfMeasureCommandToUnitOfMeasureTest {

	private final String ID = "1";

	private static String UOM = "Cup";

	UnitOfMeasureCommandToUnitOfMeasure commandToUnitOfMeasure;

	@BeforeEach
	void setUp() throws Exception {

		commandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	void testNullParameter() {
		assertNull(commandToUnitOfMeasure.convert(null));
	}

	@Test 
	void testEmptyObject() {
		assertNotNull(commandToUnitOfMeasure.convert(new UnitOfMeasureCommand()));
		
	}

	@Test
	void convert() {
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(ID);
		command.setUom(UOM);
		UnitOfMeasure measure = commandToUnitOfMeasure.convert(command);
		assertEquals(measure.getId(), command.getId());
		assertEquals(measure.getUom(), command.getUom());

	}

}
