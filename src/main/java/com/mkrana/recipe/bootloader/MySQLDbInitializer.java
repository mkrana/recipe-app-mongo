package com.mkrana.recipe.bootloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.mkrana.recipe.domain.UnitOfMeasure;
import com.mkrana.recipe.repositories.UnitOfMeasureRepository;

@Component
@Profile(value = { "dev", "prod" })
public class MySQLDbInitializer implements ApplicationRunner {

	UnitOfMeasureRepository unitOfMeasureRepository;

	public MySQLDbInitializer(UnitOfMeasureRepository unitOfMeasureRepository) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (unitOfMeasureRepository.count() == 0) {
			loadUnitOfMeasure();
		}
	}

	private void loadUnitOfMeasure() {
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Large").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Teaspoon").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Tablespoon").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Pinch").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Cup").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Ounce").build());
	}

}
