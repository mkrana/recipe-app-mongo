package com.mkrana.recipe.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.converter.IngredientCommandToIngredient;
import com.mkrana.recipe.converter.IngredientToIngredientCommand;
import com.mkrana.recipe.domain.Ingredient;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import com.mkrana.recipe.service.IngredientService;

import reactor.core.publisher.Mono;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeReactiveRepository recipeReactiveRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


	public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
		super();
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
	}

	@Override
	public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		return recipeReactiveRepository.findById(recipeId).flatMapIterable(Recipe::getIngredients)
				.filter(ingredient -> ingredientId.equalsIgnoreCase(ingredient.getId())).single().map(ingredient -> {
					IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
					command.setRecipeId(recipeId);
					return command;
				});

	}

	@Override
	public Mono<IngredientCommand> saveOrUpdateIngredient(IngredientCommand ingredientCommand) {
		Recipe savedEntity = recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).block();
		Optional<Ingredient> savedIngredientEntity = savedEntity.getIngredients().stream()
				.filter(ingredient -> ingredientCommand.getId().equalsIgnoreCase(ingredient.getId())).findFirst();
		if (savedIngredientEntity.isPresent()) {
			Ingredient ingredient = savedIngredientEntity.get();
			ingredient.setAmount(ingredientCommand.getAmount());
			ingredient.setDescription(ingredientCommand.getDescription());
			ingredient.setUnitOfMeasure(unitOfMeasureReactiveRepository.findById(ingredientCommand.getId()).block());
		} else {
			Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
			savedEntity.getIngredients().add(ingredient);
			recipeReactiveRepository.save(savedEntity).block();
		}

		// Get the saved ingredient, convert it to ingredientCommand and return the
		// stream.
		savedEntity = recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).block();
		savedIngredientEntity = savedEntity.getIngredients().stream()
				.filter(ingredient -> ingredientCommand.getId().equalsIgnoreCase(ingredient.getId())).findFirst();
		if (savedIngredientEntity.isEmpty()) {
			// Start matching all the ingredients from the saved list and return the one
			// that matches
			savedIngredientEntity = savedEntity.getIngredients().stream()
					.filter(ingredient -> ingredientCommand.getAmount().equals(ingredient.getAmount()))
					.filter(ingredient -> ingredientCommand.getDescription()
							.equalsIgnoreCase(ingredient.getDescription()))
					.filter(ingredient -> ingredientCommand.getUnitOfMeasure().getId()
							.equals(ingredient.getUnitOfMeasure().getId()))
					.findFirst();

		}
		IngredientCommand command = ingredientToIngredientCommand.convert(savedIngredientEntity.get());
		command.setRecipeId(savedEntity.getId());
		return Mono.just(command);

	}

	@Override
	public Mono<Void> deleteIngredient(String recipeId, String ingredientId) {
		Recipe savedRecipe = recipeReactiveRepository.findById(recipeId).block();
		if (savedRecipe != null) {
			Optional<Ingredient> ingredientToBeDeleted = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredientId.equalsIgnoreCase(ingredient.getId())).findFirst();
			if (ingredientToBeDeleted.isPresent()) {
				savedRecipe.getIngredients().remove(ingredientToBeDeleted.get());
				recipeReactiveRepository.save(savedRecipe);
			}
		}
		return Mono.empty();
	}
}
