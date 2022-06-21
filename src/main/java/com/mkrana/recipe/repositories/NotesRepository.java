package com.mkrana.recipe.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mkrana.recipe.domain.Notes;

public interface NotesRepository extends MongoRepository<Notes, String> {

}
