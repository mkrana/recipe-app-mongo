package com.mkrana.recipe.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.RecipeRepository;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.service.ImageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	RecipeReactiveRepository recipeRepositoy;

	public ImageServiceImpl(RecipeReactiveRepository recipeRepositoy) {
		this.recipeRepositoy = recipeRepositoy;
	}

	@Override
	public Mono<Void> saveImage(String recipeId, MultipartFile multipartFile) {
		try {
			Optional<Recipe> savedEntity = recipeRepositoy.findById(recipeId).blockOptional();
			if (savedEntity.isPresent()) {
				Recipe savedRecipe = savedEntity.get();
				Byte[] imageByteArray = new Byte[multipartFile.getBytes().length];
				int i = 0;
				for (byte b : multipartFile.getBytes()) {
					imageByteArray[i++] = b;
				}
				savedRecipe.setImage(imageByteArray);
				recipeRepositoy.save(savedRecipe).block();
				log.info("File Recieved");
			} else {
				log.error("Recipe Not Found");
			}

		} catch (IOException e) {
			log.error("Shit went south" + e.getMessage());
		}
		return Mono.empty();
	}

}
