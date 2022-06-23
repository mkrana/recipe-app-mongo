package com.mkrana.recipe.service;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

	public Flux<Recipe> getAllRecipes();

	public Mono<Recipe> findRecipeById(String id);

	public Mono<RecipeCommand> saveRecipe(RecipeCommand recipeCommand);

	public Mono<RecipeCommand> findRecipeCommandById(String id);

	public Mono<Void> deleteRecipeById(String id);

}
