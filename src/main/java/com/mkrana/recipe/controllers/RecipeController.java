
package com.mkrana.recipe.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.exceptions.NotFoundException;
import com.mkrana.recipe.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {

	private static final String RECIPEFORM = "recipe/newrecipe";

	private final RecipeService recipeService;

	private static final String RECIPE = "recipe";

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping("/recipe/{id}/show")
	public String getRecipeById(@PathVariable String id, Model model) {
		model.addAttribute(RECIPE, recipeService.findRecipeById(id));
		return "recipe/show";
	}

	@GetMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute(RECIPE, new Recipe());
		return RECIPEFORM;
	}

	@PostMapping("/recipe/save")
	public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
			BindingResult result) {
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> log.error(error.toString()));
			return RECIPEFORM;
		}
		RecipeCommand savedEntity = recipeService.saveRecipe(recipeCommand).block();
		return "redirect:/recipe/" + savedEntity.getId() + "/show";
	}

	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		RecipeCommand savedRecipe = recipeService.findRecipeCommandById(id).block();
		model.addAttribute(RECIPE, savedRecipe);
		return RECIPEFORM;

	}

	@GetMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		recipeService.deleteRecipeById(id);
		return "redirect:/";

	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404notfound");
		modelAndView.addObject("exception", exception);
		return modelAndView;

	}

}
