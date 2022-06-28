package com.mkrana.recipe.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.service.ImageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	RecipeReactiveRepository recipeRepository;

	public ImageServiceImpl(RecipeReactiveRepository recipeRepositoy) {
		this.recipeRepository = recipeRepositoy;
	}

	@Override
	public Mono<Void> saveImage(String recipeId, MultipartFile multipartFile) {
		try {
			Recipe savedEntity = recipeRepository.findById(recipeId).block();
			Byte[] imageByteArray = new Byte[multipartFile.getBytes().length];
			int i = 0;
			for (byte b : multipartFile.getBytes()) {
				imageByteArray[i++] = b;
			}
			savedEntity.setImage(imageByteArray);
			recipeRepository.save(savedEntity).block();
			log.info("File Recieved");
		}
		catch (IOException e) {
			log.error("Shit went south" + e.getMessage());
		}
		return Mono.empty();
	}

}
