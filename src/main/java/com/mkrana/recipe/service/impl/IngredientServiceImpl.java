package com.mkrana.recipe.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.converter.IngredientCommandToIngredient;
import com.mkrana.recipe.converter.IngredientToIngredientCommand;
import com.mkrana.recipe.domain.Ingredient;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.IngredientRepository;
import com.mkrana.recipe.repositories.RecipeRepository;
import com.mkrana.recipe.repositories.UnitOfMeasureRepository;
import com.mkrana.recipe.service.IngredientService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	private final UnitOfMeasureRepository unitOfMeasureRepository;

	private final IngredientRepository ingredientRepository;

	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			UnitOfMeasureRepository unitOfMeasureRepository,
			IngredientCommandToIngredient ingredientCommandToIngredient, IngredientRepository ingredientRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	@Transactional
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
		Optional<Recipe> savedRecipe = recipeRepository.findById(recipeId);
		if (!savedRecipe.isPresent()) {
			// TODO Error Messages to be sent out.
		}

		log.info("Size of the argument-" + savedRecipe.get().getIngredients().size());

		Optional<IngredientCommand> savedIngredient = savedRecipe.get().getIngredients().stream()
				.filter(ingredient -> ingredientId.equals(ingredient.getId()))
				.map(ingredientToIngredientCommand::convert).findFirst();

		return savedIngredient.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand) {
		Optional<Recipe> savedEntity = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (savedEntity.isPresent()) {
			Recipe recipe = savedEntity.get();
			Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
			if (optionalIngredient.isPresent()) {
				optionalIngredient.get().setAmount(ingredientCommand.getAmount());
				optionalIngredient.get().setDescription(ingredientCommand.getDescription());
				optionalIngredient.get()
						.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
								.orElseThrow(() -> new RuntimeException("No Such UOM")));
			} else {
				Ingredient detachedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
				detachedIngredient.setRecipe(recipe);
				recipe.getIngredients().add(detachedIngredient);
			}

			Recipe savedRecipe = recipeRepository.save(recipe);

			Optional<IngredientCommand> optionalIngredientCommand = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
					.filter(ingredient -> ingredient.getUnitOfMeasure().getId()
							.equals(ingredientCommand.getUnitOfMeasure().getId()))
					.filter(ingredient -> ingredient.getDescription()
							.equalsIgnoreCase(ingredientCommand.getDescription()))
					.map(ingredientToIngredientCommand::convert).findFirst();
			return optionalIngredientCommand.orElse(null);
		} else {
			log.error("No Such Recipe Exists");
			// This should actually be thrown as an error, what's a graceful way to code
			// this?
			return (new IngredientCommand());
		}

	}

	@Override
	public void deleteIngredient(String recipeId, String ingredientId) {
		Optional<Recipe> savedEntity = recipeRepository.findById(recipeId);
		if (!savedEntity.isPresent()) {
			log.error("Recipe Not Found");
		} else {
			Recipe savedRecipe = savedEntity.get();
			Optional<Ingredient> savedIngredientEntity = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredientId.equals(ingredient.getId())).findFirst();
			if (!savedIngredientEntity.isPresent()) {
				log.error("Ingredient Not Found");
			} else {
				Ingredient ingredientToBeDeleted = savedIngredientEntity.get();
				savedRecipe.getIngredients().remove(ingredientToBeDeleted);
				ingredientRepository.delete(ingredientToBeDeleted);
			}
		}
	}
}
