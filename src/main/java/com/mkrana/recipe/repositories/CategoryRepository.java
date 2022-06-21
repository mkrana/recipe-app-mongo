package com.mkrana.recipe.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.mkrana.recipe.domain.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

	Optional<Category> findByDescription(String description);

}
