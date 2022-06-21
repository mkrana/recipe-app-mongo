package com.mkrana.recipe.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Recipe;

@Service
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	NotesToNotesCommand notesToNotesCommand;

	IngredientToIngredientCommand ingredientToIngredientCommand;

	CategoryToCategoryCommand categorytoCategoryCommand;

	public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			CategoryToCategoryCommand categorytoCategoryCommand) {
		this.notesToNotesCommand = notesToNotesCommand;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.categorytoCategoryCommand = categorytoCategoryCommand;
	}

	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setNote(notesToNotesCommand.convert(source.getNote()));
		if (source.getCategories() != null) {
			source.getCategories().forEach(
					category -> recipeCommand.getCategories().add(categorytoCategoryCommand.convert(category)));
		}
		if (source.getIngredients() != null) {
			source.getIngredients().forEach(ingredient -> recipeCommand.getIngredients()
					.add(ingredientToIngredientCommand.convert(ingredient)));
		}

		return recipeCommand;

	}

}
