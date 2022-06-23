package com.mkrana.recipe.service;

import com.mkrana.recipe.command.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
	
	public Flux<UnitOfMeasureCommand> allUnitOfMeasure();

}
