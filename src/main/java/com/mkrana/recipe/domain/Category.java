package com.mkrana.recipe.domain;

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
@Builder
@EqualsAndHashCode(exclude = { "recipies" })
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Category {

	@Id
	private String id;
	private String description;
	private Set<Recipe> recipies;

}
