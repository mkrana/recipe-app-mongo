package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.CategoryCommand;
import com.mkrana.recipe.domain.Category;

class CategoryCommandToCategoryTest {
	
	private final String ID = "1";
	private final String DESCRIPTION = "Breakfast";

	CategoryCommandToCategory converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));
	}
	
	void testEmptyObject() {
		assertNotNull(converter.convert(new CategoryCommand()));
	}

	@Test
	void testConvert() {
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setDescription(DESCRIPTION);
		categoryCommand.setId(ID);
		Category category = converter.convert(categoryCommand);
		assertEquals(category.getId(), categoryCommand.getId());
		assertEquals(category.getDescription(), categoryCommand.getDescription());
	}

}
