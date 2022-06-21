package com.mkrana.recipe.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mkrana.recipe.command.UnitOfMeasureCommand;
import com.mkrana.recipe.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (source == null) {
			return null;
		}
		UnitOfMeasureCommand destination = new UnitOfMeasureCommand();
		destination.setId(source.getId());
		destination.setUom(source.getUom());
		return destination;
	}

}
