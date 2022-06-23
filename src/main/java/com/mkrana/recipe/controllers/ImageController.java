package com.mkrana.recipe.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mkrana.recipe.command.RecipeCommand;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.service.ImageService;
import com.mkrana.recipe.service.RecipeService;

@Controller
public class ImageController {

	private final RecipeService recipeService;

	private final ImageService imageService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@GetMapping("recipe/{recipeId}/imageform")
	public String loadImageUploadForm(@PathVariable String recipeId, Model model) {
		Recipe formRecipe = recipeService.findRecipeById(recipeId).block();
		model.addAttribute("recipe", formRecipe);
		return "recipe/imageform";
	}

	@PostMapping("/recipe/{recipeId}/saveimage")
	public String uploadFile(@PathVariable String recipeId, @RequestParam("recipeImage") MultipartFile recipeImage) {
		imageService.saveImage(recipeId, recipeImage);
		return "redirect:/recipe/" + recipeId + "/show";
	}

	@GetMapping("/recipe/{recipeId}/renderimage")
	public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findRecipeCommandById(recipeId).block();
		// Convert Primitive array to Boxed array
		if (recipeCommand.getImage() != null) {
			byte[] byteImageArray = new byte[recipeCommand.getImage().length];
			int i = 0;
			for (Byte byteIter : recipeCommand.getImage()) {
				byteImageArray[i++] = byteIter;
			}
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteImageArray);
			IOUtils.copy(is, response.getOutputStream());
		}
	}

}
