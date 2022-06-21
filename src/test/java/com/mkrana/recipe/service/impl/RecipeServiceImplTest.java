package com.mkrana.recipe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mkrana.recipe.converter.RecipeCommandToRecipe;
import com.mkrana.recipe.converter.RecipeToRecipeCommand;
import com.mkrana.recipe.domain.Notes;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.exceptions.NotFoundException;
import com.mkrana.recipe.repositories.RecipeRepository;

class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	Recipe recipe;

	@Captor
	ArgumentCaptor<Notes> noteArgument;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

//	@Test
//	void test() {
//		Recipe recipe = new Recipe();
//		Set<Recipe> recipeSet = new HashSet<>();
//		recipeSet.add(recipe);
//		when(recipeRepository.findAll()).thenReturn(recipeSet);
//		assertEquals(recipeService.getAllRecipes().size(), 1);
//		verify(recipeRepository, times(1)).findAll();
//
//	}

	@Test
	void createNewRecipeTest() {
		recipeService.createNewRecipe(recipe);
		verify(recipe).setNote(noteArgument.capture());
		Notes note = noteArgument.getValue();
		assertEquals("Recipe Note", note.getRecipeNotes());
	}

	@Test
	void getRecipeByIdTest() {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
		Recipe recipeReturned = recipeService.findRecipeById("1");
		assertNotNull(recipeReturned);
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, never()).findAll();

	}

	@Test
	void deleteByRecipeIdTest() throws Exception {
		recipeService.deleteRecipeById("1");
		verify(recipeRepository, times(1)).deleteById(anyString());

	}

	@Test
	void getRecipeByIdNotFound() throws Exception {
		assertThrows(NotFoundException.class, () -> recipeService.findRecipeById("3"));
	}

}
