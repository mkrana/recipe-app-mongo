package com.mkrana.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mkrana.recipe.domain.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
