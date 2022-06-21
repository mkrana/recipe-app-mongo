package com.mkrana.recipe.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	RecipeRepository recipeRepositoy;

	public ImageServiceImpl(RecipeRepository recipeRepositoy) {
		this.recipeRepositoy = recipeRepositoy;
	}

	@Override
	public void saveImage(String recipeId, MultipartFile multipartFile) {
		try {
			Optional<Recipe> savedEntity = recipeRepositoy.findById(recipeId);
			if (savedEntity.isPresent()) {
				Recipe savedRecipe = savedEntity.get();
				Byte[] imageByteArray = new Byte[multipartFile.getBytes().length];
				int i = 0;
				for (byte b : multipartFile.getBytes()) {
					imageByteArray[i++] = b;
				}
				savedRecipe.setImage(imageByteArray);
				recipeRepositoy.save(savedRecipe);
				log.info("File Recieved");
			} else {
				// TODO Throw an exception. This has to be a universal
				log.error("Recipe Not Found");
			}
		} catch (IOException e) {
			log.error("Shit went south" + e.getMessage());
		}
	}

}
