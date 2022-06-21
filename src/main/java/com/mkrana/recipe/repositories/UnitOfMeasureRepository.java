package com.mkrana.recipe.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.mkrana.recipe.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends MongoRepository<UnitOfMeasure, String> {

	Optional<UnitOfMeasure> findByUom(String uom);
	
}
