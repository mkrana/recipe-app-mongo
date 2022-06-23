package com.mkrana.recipe.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.converter.RecipeCommandToRecipe;
import com.mkrana.recipe.converter.RecipeToRecipeCommand;
import com.mkrana.recipe.domain.Difficulty;
import com.mkrana.recipe.domain.Notes;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.exceptions.NotFoundException;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.service.RecipeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeRepository;

	private final RecipeCommandToRecipe recipeCommandToRecipe;

	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getAllRecipes() {
		return recipeRepository.findAll();
	}

	public Recipe createNewRecipe(Recipe freshBakedCookies) {
		freshBakedCookies.setDifficulty(Difficulty.EASY);
		Notes randomnote = new Notes();
		randomnote.setRecipeNotes("Recipe Note");
		freshBakedCookies.setNote(randomnote);
		return freshBakedCookies;
	}

	@Transactional
	public Mono<Recipe> findRecipeById(String id) {
		return recipeRepository.findById(id);
	}

	@Override
	public Mono<RecipeCommand> saveRecipe(RecipeCommand recipeCommand) {
		Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
		Recipe savedRecipe = recipeRepository.save(recipe).block();
		return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
	}

	@Override
	@Transactional
	public Mono<RecipeCommand> findRecipeCommandById(String id) {
		Optional<Recipe> savedRecipe = recipeRepository.findById(id).blockOptional();
		RecipeCommand savedRecipeCommand;
		if (savedRecipe.isPresent())
			savedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe.get());
		else
			throw new NotFoundException("No Recipe Found");

		return Mono.just(savedRecipeCommand);
	}

	public Mono<Void> deleteRecipeById(String id) {
		recipeRepository.deleteById(id);
		return Mono.empty();
	}

}
