package com.mkrana.recipe.service.impl;

import org.springframework.stereotype.Service;

import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.mkrana.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import com.mkrana.recipe.service.UnitOfMeasureService;

import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		super();
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public Flux<UnitOfMeasureCommand> allUnitOfMeasure() {
		return unitOfMeasureRepository.findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
	}
}
