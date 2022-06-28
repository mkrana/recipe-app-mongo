package com.mkrana.recipe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.service.impl.ImageServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

	private static final String ID = "1L";

	private static final String mockImageFile = "RandomGibberishToBeTested";

	@Mock
	RecipeReactiveRepository recipeRepository;

	@InjectMocks
	ImageServiceImpl imageService;

	@Captor
	ArgumentCaptor<Recipe> recipeCaptor;

	@Test
	void testImageUpload() throws IOException {
		Recipe recipe = new Recipe();
		recipe.setId(ID);
		when(recipeRepository.findById(ID)).thenReturn(Mono.just(recipe));
		when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));
		MockMultipartFile imageFile = new MockMultipartFile("recipeimage", "randomimagefile.txt", "text/plain",
				mockImageFile.getBytes());
		imageService.saveImage(ID, imageFile).block();
		verify(recipeRepository, times(1)).save(recipeCaptor.capture());
		Recipe recipeArgument = recipeCaptor.getValue();
		assertEquals(imageFile.getBytes().length, recipeArgument.getImage().length);
	}

}
