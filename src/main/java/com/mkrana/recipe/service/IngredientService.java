package com.mkrana.recipe.service;

import com.mkrana.recipe.command.IngredientCommand;

public interface IngredientService {
	
	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	
	IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand);
	
	void deleteIngredient(String recipeId, String ingredientId);
	

}
