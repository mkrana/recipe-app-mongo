package com.mkrana.recipe.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	IngredientCommandToIngredient ingredientCommandToIngredient;
	CategoryCommandToCategory categoryCommandToCategory;
	NotesCommandToNotes notesCommandToNotes;

	public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
			CategoryCommandToCategory categoryCommandToCategory, NotesCommandToNotes notesCommandToNotes) {
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.categoryCommandToCategory = categoryCommandToCategory;
		this.notesCommandToNotes = notesCommandToNotes;
	}

	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}

		Recipe returnObject = new Recipe();
		returnObject.setId(source.getId());
		returnObject.setDescription(source.getDescription());
		returnObject.setCookTime(source.getCookTime());
		returnObject.setPrepTime(source.getPrepTime());
		returnObject.setSource(source.getSource());
		returnObject.setUrl(source.getUrl());
		returnObject.setDirections(source.getDirections());
		returnObject.setServings(source.getServings());
		returnObject.setDifficulty(source.getDifficulty());
		returnObject.setImage(source.getImage());
		returnObject.setNote(notesCommandToNotes.convert(source.getNote()));
		if (source.getCategories() != null) {
			source.getCategories()
					.forEach(category -> returnObject.getCategories().add(categoryCommandToCategory.convert(category)));
		}
		if (source.getIngredients() != null) {
			source.getIngredients().forEach(
					ingredient -> returnObject.getIngredients().add(ingredientCommandToIngredient.convert(ingredient)));
		}

		return returnObject;
	}

}
