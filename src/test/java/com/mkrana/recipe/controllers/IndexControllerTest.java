
package com.mkrana.recipe.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.service.RecipeService;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

	@InjectMocks
	IndexController indexController;

	@Mock
	RecipeService recipeService;

	@Mock
	Model model;

	@Captor
	ArgumentCaptor<List<Recipe>> argumentRecipeCaptor;

	@Test
	void testGetIndexPage() {

		// given
		Recipe recipe = new Recipe();
		Recipe secondRecipe = new Recipe();
		secondRecipe.setId("2");
		// when
		when(recipeService.getAllRecipes())
				.thenReturn(Flux.just(Recipe.builder().id("1").build(), Recipe.builder().id("2").build()));

		assertEquals("index", indexController.getIndexPage(model));
		verify(recipeService, times(1)).getAllRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentRecipeCaptor.capture());

		// Argument Captor
		assertEquals(2, argumentRecipeCaptor.getValue().size());
	}

	@Test
	void mockMvcTest() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));

	}

}
