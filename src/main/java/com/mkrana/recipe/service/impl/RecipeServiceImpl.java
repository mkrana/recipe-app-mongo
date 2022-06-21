package com.mkrana.recipe.service.impl;

import java.util.ArrayList;
import java.util.List;
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
import com.mkrana.recipe.repositories.RecipeRepository;
import com.mkrana.recipe.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

	private final RecipeCommandToRecipe recipeCommandToRecipe;

	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		List<Recipe> listOfRecipes = new ArrayList<>();
		recipeRepository.findAll().iterator().forEachRemaining(listOfRecipes::add);
		return listOfRecipes;
	}

	public Recipe createNewRecipe(Recipe freshBakedCookies) {
		freshBakedCookies.setDifficulty(Difficulty.EASY);
		Notes randomnote = new Notes();
		randomnote.setRecipeNotes("Recipe Note");
		freshBakedCookies.setNote(randomnote);
		return freshBakedCookies;
	}

	@Transactional
	public Recipe findRecipeById(String id) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if (!recipe.isPresent()) {
			throw new NotFoundException("No Such Recipe Exists. For ID Value: " + id.toString());
		}
		return recipe.get();
	}

	@Override
	public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
		Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
		log.info(recipeCommand.toString());
		Recipe savedRecipe = recipeRepository.save(recipe);
		log.info("Recipe object saved");
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	@Transactional
	public RecipeCommand findRecipeCommandById(String id) {
		Optional<Recipe> savedRecipe = recipeRepository.findById(id);
		RecipeCommand savedRecipeCommand;
		if (savedRecipe.isPresent())
			savedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe.get());
		else
			throw new NotFoundException("No Recipe Found");

		return savedRecipeCommand;
	}

	public void deleteRecipeById(String id) {
		recipeRepository.deleteById(id);
	}

}
