package com.mkrana.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.RecipeRepository;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

	private static final String ID = "1L";

	private static final String mockImageFile = "RandomGibberishToBeTested";

	@Mock
	RecipeRepository recipeRepository;

	@InjectMocks
	ImageServiceImpl imageService;

	@Captor
	ArgumentCaptor<Recipe> recipeCaptor;

	@Test
	void testImageUpload() throws IOException {
		Recipe recipe = new Recipe();
		recipe.setId(ID);
		when(recipeRepository.findById(ID)).thenReturn(Optional.of(recipe));
		MockMultipartFile imageFile = new MockMultipartFile("recipeimage", "randomimagefile.txt", "text/plain",
				mockImageFile.getBytes());
		imageService.saveImage(ID, imageFile);
		verify(recipeRepository, times(1)).save(recipeCaptor.capture());
		Recipe recipeArgument = recipeCaptor.getValue();
		assertEquals(imageFile.getBytes().length, recipeArgument.getImage().length);
	}

}
