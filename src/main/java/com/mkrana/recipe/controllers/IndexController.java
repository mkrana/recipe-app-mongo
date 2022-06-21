package com.mkrana.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mkrana.recipe.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping({ "", "/", "/index", "index.html", "/index.html" })
	public String getIndexPage(Model model) {
		log.debug("getIndexPage() invoked");
		model.addAttribute("recipes",recipeService.getAllRecipes());
		return "index";
	}

}
