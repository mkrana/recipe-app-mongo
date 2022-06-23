package com.mkrana.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mkrana.recipe.domain.Category;

import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

	Mono<Category> findByDescription(String description);

}
