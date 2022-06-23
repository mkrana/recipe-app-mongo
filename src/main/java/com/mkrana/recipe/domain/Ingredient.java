package com.mkrana.recipe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "recipe" })
public class Ingredient {

	private String id = UUID.randomUUID().toString();
	private String description;
	private BigDecimal amount;
	private UnitOfMeasure unitOfMeasure;
	private Recipe recipe;

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeasure = unitOfMeasure;
	}
}
