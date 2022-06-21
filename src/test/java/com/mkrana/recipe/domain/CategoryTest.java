package com.mkrana.recipe.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryTest {

	Category category = new Category();

	@Test
	void testGetId() {
		String initialvalue = "4";
		category.setId(initialvalue);
		assertEquals(initialvalue, category.getId());
	}

}
