package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.CategoryCommand;
import com.mkrana.recipe.domain.Category;

class CategoryToCategoryCommandTest {
	private final String ID = "1";
	private final String DESCRIPTION = "Dinner";
	CategoryToCategoryCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));
	}

	void testEmptyObject() {
		assertNotNull(converter.convert(new Category()));
	}

	@Test
	void testConvert() {
		Category category = new Category();
		category.setId(ID);
		category.setDescription(DESCRIPTION);
		CategoryCommand categoryCommand = converter.convert(category);
		assertEquals(categoryCommand.getId(), category.getId());
		assertEquals(categoryCommand.getDescription(), category.getDescription());
	}

}
