
package com.mkrana.recipe.command;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.mkrana.recipe.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private String id;

	@NotBlank
	@Size(min = 3, max = 255)
	private String description;

	@Min(1)
	@Max(999)
	@NotNull
	private Integer prepTime;

	@Min(1)
	@Max(999)
	@NotNull
	private Integer cookTime;

	@Min(1)
	@Max(100)
	@NotNull
	private Integer servings;
	private String source;

	@URL
	private String url;

	@NotBlank
	private String directions;
	private Byte[] image;
	private NotesCommand note;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Difficulty difficulty;
	private Set<CategoryCommand> categories = new HashSet<>();

}
