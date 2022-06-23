package com.mkrana.recipe.service;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface ImageService {

	public Mono<Void> saveImage(String recipeId, MultipartFile multipartFile);

}
