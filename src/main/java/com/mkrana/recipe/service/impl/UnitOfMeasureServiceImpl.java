package com.mkrana.recipe.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.mkrana.recipe.repositories.UnitOfMeasureRepository;
import com.mkrana.recipe.service.UnitOfMeasureService;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;

	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		super();
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public Set<UnitOfMeasureCommand> allUnitOfMeasure() {
		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
				.map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
	}

}
