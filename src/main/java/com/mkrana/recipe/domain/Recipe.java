
package com.mkrana.recipe.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = { "ingredients" })
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

	@Id
	private String id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Byte[] image;
	private Notes note;
	private Set<Ingredient> ingredients = new HashSet<>();
	private Difficulty difficulty;
	private Set<Category> categories = new HashSet<>();

	public void setNote(Notes note) {
		if (note != null) {
			this.note = note;
		}
	}

	public void addIngredients(Ingredient ingredient) {
		if (ingredient != null) {
			ingredients.add(ingredient);
		}
	}

}
