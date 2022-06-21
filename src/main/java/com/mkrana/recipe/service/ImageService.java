package com.mkrana.recipe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	public void saveImage(String recipeId, MultipartFile multipartFile);

}
