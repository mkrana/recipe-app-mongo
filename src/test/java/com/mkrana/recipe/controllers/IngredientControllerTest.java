package com.mkrana.recipe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.service.IngredientService;
import com.mkrana.recipe.service.RecipeService;
import com.mkrana.recipe.service.UnitOfMeasureService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

	private final String ID = "1";

	private final String ID_2 = "2";

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredientService;

	@Mock
	UnitOfMeasureService unitOfMeasureService;

	MockMvc ingredientMvc;

	@BeforeEach
	void setUp() throws Exception {
		ingredientMvc = MockMvcBuilders
				.standaloneSetup(new IngredientController(recipeService, ingredientService, unitOfMeasureService))
				.setControllerAdvice(GlobalExceptionHandler.class).build();
	}

	@Test
	void testListIngredients() throws Exception {
		when(recipeService.findRecipeCommandById(anyString())).thenReturn(Mono.just(new RecipeCommand()));
		ingredientMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientlist"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	void testViewIngredient() throws Exception {
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
				.thenReturn(Mono.just(new IngredientCommand()));
		ingredientMvc.perform(get("/recipe/1/ingredients/1/show")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show")).andExpect(model().attributeExists("ingredient"));
	}

	@Test
	void testUpdateIngredientForm() throws Exception {
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
				.thenReturn(Mono.just(new IngredientCommand()));
		when(unitOfMeasureService.allUnitOfMeasure())
				.thenReturn(Flux.just(UnitOfMeasureCommand.builder().uom("Large").build(),
						UnitOfMeasureCommand.builder().uom("Cup").build()));
		ingredientMvc.perform(get("/recipe/1/ingredients/2/update")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("unitOfMeasureList"));
	}

	@Test
	void testUpdateIngredient() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID);
		ingredientCommand.setRecipeId(ID_2);
		when(ingredientService.saveOrUpdateIngredient(any())).thenReturn(Mono.just(ingredientCommand));

		ingredientMvc
				.perform(post("/recipe/1/ingredients/save").contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", ID).param("amount", "24").param("recipeId", ID))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/ingredients/1/show"));
	}

	@Test
	void testNewIngredientForm() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(ID);
		when(recipeService.findRecipeById(ID)).thenReturn(Mono.just(recipe));
		when(unitOfMeasureService.allUnitOfMeasure())
				.thenReturn(Flux.just(UnitOfMeasureCommand.builder().uom("Large").build(),
						UnitOfMeasureCommand.builder().uom("Cup").build()));
		ingredientMvc.perform(get("/recipe/1/ingredients/new")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("unitOfMeasureList"))
				.andExpect(model().attributeExists("ingredient"));

	}

	@Test
	void testDeleteIngredient() throws Exception {
		ingredientMvc.perform(get("/recipe/1/ingredients/2/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients"));
		verify(ingredientService).deleteIngredient(anyString(), anyString());
	}

}
