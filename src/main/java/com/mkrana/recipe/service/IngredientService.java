package com.mkrana.recipe.service;

import com.mkrana.recipe.command.IngredientCommand;

import reactor.core.publisher.Mono;

public interface IngredientService {
	
	Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	
	Mono<IngredientCommand> saveOrUpdateIngredient(IngredientCommand ingredientCommand);
	
	Mono<Void> deleteIngredient(String recipeId, String ingredientId);
	

}
