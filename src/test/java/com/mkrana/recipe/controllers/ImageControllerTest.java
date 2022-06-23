package com.mkrana.recipe.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.service.impl.ImageServiceImpl;
import com.mkrana.recipe.service.impl.RecipeServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

	private static final String ID = "1";

	private static final String mockImageFile = "RandomGibberishToBeTested";

	@Mock
	ImageServiceImpl imageService;

	@Mock
	RecipeServiceImpl recipeService;

	ImageController imageController;

	MockMvc imageControllerMvc;

	@BeforeEach
	void setUp() throws Exception {
		imageController = new ImageController(imageService, recipeService);
		imageControllerMvc = MockMvcBuilders.standaloneSetup(imageController)
				.setControllerAdvice(GlobalExceptionHandler.class).build();
	}

	@Test
	void testFileUploadForm() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(ID);
		when(recipeService.findRecipeById(ID)).thenReturn(Mono.just(recipe));
		imageControllerMvc.perform(get("/recipe/" + ID + "/imageform")).andExpect(status().isOk())
				.andExpect(view().name("recipe/imageform")).andExpect((model().attributeExists("recipe")));

	}

	@Test
	void testFileUpload() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile("recipeImage", "randomGibberish", "text/plain",
				mockImageFile.getBytes());
		// Test if the file is indeed getting uploaded
		imageControllerMvc.perform(multipart("/recipe/1/saveimage").file(mockFile))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/" + ID + "/show"));
		verify(imageService, times(1)).saveImage(ID, mockFile);

	}

	@Test
	void testImageRender() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile("recipeImage", "randomGibberish", "text/plain",
				mockImageFile.getBytes());
		RecipeCommand recipe = new RecipeCommand();
		recipe.setId(ID);

		Byte[] boxedByteArray = new Byte[mockFile.getBytes().length];
		int i = 0;
		for (byte byteIter : mockFile.getBytes()) {
			boxedByteArray[i++] = byteIter;
		}
		recipe.setImage(boxedByteArray);

		when(recipeService.findRecipeCommandById(ID)).thenReturn(Mono.just(recipe));

		MockHttpServletResponse httpServletResponse = imageControllerMvc.perform(get("/recipe/1/renderimage"))
				.andExpect(status().isOk()).andReturn().getResponse();
		assertEquals(boxedByteArray.length, httpServletResponse.getContentAsByteArray().length);
	}

}
