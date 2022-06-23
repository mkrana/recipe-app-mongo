package com.mkrana.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.service.IngredientService;
import com.mkrana.recipe.service.RecipeService;
import com.mkrana.recipe.service.UnitOfMeasureService;

@Controller
public class IngredientController {

	RecipeService recipeService;

	IngredientService ingredientService;

	UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.unitOfMeasureService = unitOfMeasureService;
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findRecipeCommandById(recipeId));
		return "recipe/ingredient/ingredientlist";
	}

	@RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
		return "recipe/ingredient/show";
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/update")
	public String loadIngredientForm(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
		model.addAttribute("unitOfMeasureList", unitOfMeasureService.allUnitOfMeasure());
		return "recipe/ingredient/ingredientform";

	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		// Check if recipe with the id exists
		Recipe recipe = recipeService.findRecipeById(recipeId).block();
		if (recipe == null) {
			// Something has gone really wrong
		}
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		ingredientCommand.setRecipeId(recipe.getId());
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("unitOfMeasureList", unitOfMeasureService.allUnitOfMeasure());

		return "recipe/ingredient/ingredientform";
	}

	@PostMapping
	@RequestMapping("/recipe/{recipeId}/ingredients/save")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedIngredientCommand = ingredientService.saveOrUpdateIngredient(ingredientCommand).block();
		return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredients/"
				+ savedIngredientCommand.getId() + "/show";

	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		ingredientService.deleteIngredient(recipeId, ingredientId);
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}

}
