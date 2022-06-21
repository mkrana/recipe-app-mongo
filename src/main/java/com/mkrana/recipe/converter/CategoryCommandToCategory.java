package com.mkrana.recipe.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mkrana.recipe.command.CategoryCommand;
import com.mkrana.recipe.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null)
			return null;
		Category category = new Category();
		category.setId(source.getId());
		category.setDescription(source.getDescription());
		return category;
	}

}
