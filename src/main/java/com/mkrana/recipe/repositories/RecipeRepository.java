package com.mkrana.recipe.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.mkrana.recipe.domain.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

}
