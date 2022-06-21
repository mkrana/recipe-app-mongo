package com.mkrana.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mkrana.recipe.command.IngredientCommand;
import com.mkrana.recipe.converter.IngredientCommandToIngredient;
import com.mkrana.recipe.converter.IngredientToIngredientCommand;
import com.mkrana.recipe.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.mkrana.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.mkrana.recipe.domain.Ingredient;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.IngredientRepository;
import com.mkrana.recipe.repositories.RecipeRepository;
import com.mkrana.recipe.repositories.UnitOfMeasureRepository;
import com.mkrana.recipe.service.impl.IngredientServiceImpl;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

	private final String ID_1 = "1";
	private final String ID_2 = "2";

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	IngredientRepository ingredientRepository;

	IngredientToIngredientCommand ingredientToIngredientCommand;

	IngredientCommandToIngredient ingredientCommandToIngredient;

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	IngredientServiceImpl ingredientService;

	@BeforeEach
	public void setUp() {
		ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
		ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand,
				unitOfMeasureRepository, ingredientCommandToIngredient, ingredientRepository);

	}

	@Test
	void testFindByRecipeIdAndIngredientId() throws Exception {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_2);
		Recipe testRecipe = new Recipe();
		testRecipe.setId(ID_1);
		testRecipe.getIngredients().add(ingredient);
		when(recipeRepository.findById(anyString())).thenReturn(Optional.of(testRecipe));

		IngredientCommand returnedObject = ingredientService.findByRecipeIdAndIngredientId(ID_1, ID_2);

		assertEquals(returnedObject.getId(), ingredient.getId());
		verify(recipeRepository, times(1)).findById(anyString());

	}

	@Test
	void testIngredientDeletion() throws Exception {
		String ingredientToDelete = "1";
		String recipeId = "2";
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientToDelete);
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		recipe.getIngredients().add(ingredient);
		when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));
		ingredientService.deleteIngredient(recipeId, ingredientToDelete);
		verify(recipeRepository, times(1)).findById(recipeId);
		verify(ingredientRepository, times(1)).delete(ingredient);
	}

}
