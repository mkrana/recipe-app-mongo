package com.mkrana.recipe.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientCommand {

	private String id;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasureCommand unitOfMeasure;
	private String recipeId;

}
