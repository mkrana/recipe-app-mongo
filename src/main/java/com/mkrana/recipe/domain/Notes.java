package com.mkrana.recipe.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "recipe")
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "recipe" })
@Document
public class Notes {

	private String id;
	private Recipe recipe;
	private String recipeNotes;

	public Notes(String recipeNotes) {
		this.recipeNotes = recipeNotes;
	}

}
