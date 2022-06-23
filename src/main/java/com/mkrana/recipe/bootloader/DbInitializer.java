
package com.mkrana.recipe.bootloader;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.mkrana.recipe.domain.Category;
import com.mkrana.recipe.domain.Difficulty;
import com.mkrana.recipe.domain.Ingredient;
import com.mkrana.recipe.domain.Notes;
import com.mkrana.recipe.domain.Recipe;
import com.mkrana.recipe.domain.UnitOfMeasure;
import com.mkrana.recipe.repositories.CategoryRepository;
import com.mkrana.recipe.repositories.RecipeRepository;
import com.mkrana.recipe.repositories.UnitOfMeasureRepository;
import com.mkrana.recipe.repositories.reactive.CategoryReactiveRepository;
import com.mkrana.recipe.repositories.reactive.RecipeReactiveRepository;
import com.mkrana.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DbInitializer implements ApplicationRunner {

	private final CategoryRepository categoryRepository;

	private final UnitOfMeasureRepository unitOfMeasureRepository;

	private final RecipeRepository recipeRepository;

	@Autowired
	UnitOfMeasureReactiveRepository reactiveUnitOfMeasureRepository;

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;

	public DbInitializer(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			RecipeRepository recipeRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.recipeRepository = recipeRepository;
	}

	private void loadUnitOfMeasure() {
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Large").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Teaspoon").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Tablespoon").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Pinch").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Cup").build());
		unitOfMeasureRepository.save(UnitOfMeasure.builder().uom("Ounce").build());
	}

	private void loadCategories() {
		categoryRepository.save(Category.builder().description("Banana").build());
		categoryRepository.save(Category.builder().description("Breakfast").build());
		categoryRepository.save(Category.builder().description("Easy Baking").build());
	}

	private void populateBananaBreadRecipe() {
		log.info("Invoked populateRecipe()");

		Optional<UnitOfMeasure> large = unitOfMeasureRepository.findByUom("Large");
		Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByUom("Cup");
		Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByUom("Teaspoon");

		Optional<Category> banana = categoryRepository.findByDescription("Banana");
		Optional<Category> breakfast = categoryRepository.findByDescription("Breakfast");
		Optional<Category> easyBaking = categoryRepository.findByDescription("Easy Baking");
		Set<Category> categories = new HashSet<>();
		categories.add(banana.get());
		categories.add(breakfast.get());
		categories.add(easyBaking.get());

		// Creating Chocolate Banana Bread Recipe
		Recipe breadRecipe = new Recipe();
		breadRecipe.setCategories(categories);
		breadRecipe.setCookTime(60);
		breadRecipe.setPrepTime(15);
		breadRecipe.setServings(12);
		breadRecipe.setDescription("Chocolate Banana Bread");
		breadRecipe.setSource("SimplyRecipes");
		breadRecipe.setUrl("https://www.simplyrecipes.com/recipes/chocolate_banana_bread");
		breadRecipe.setNote(new Notes(
				"Use very ripe or over-ripe bananas. The peels should be at least half browned, and the bananas inside squishy and browning.\n"
						+ "Do not use Dutch processed cocoa for this recipe, only natural unsweetened.\n" + "\n"
						+ "Melted coconut oil can be used in place of the butter. The flavor will change a little and you may get a hint of coconut in the result.\n"
						+ "There is only 1 egg and 1 teaspoon of baking soda in this recipe for leavening, so make sure your baking soda is still good!"));
		breadRecipe.setDirections("Prepare pan and preheat oven:\n"
				+ "Butter or spray with cooking spray the inside of a 5x9-inch loaf pan. Preheat oven to 350°F (175°C) with a rack in the middle.\n"
				+ "\n" + "Purée bananas:\n"
				+ "Using a blender, food processor, or a fork, purée the peeled ripe bananas until smooth. You should have 1 1/2 to 1 3/4 cups of banana purée.\n"
				+ "\n" + "\n" + "Mix wet ingredients and sugar:\n"
				+ "Place the banana purée into a large mixing bowl. Stir the melted butter. Stir in the brown sugar and salt. Whisk to break up any clumps of brown sugar. Stir in the beaten egg and vanilla extract.\n"
				+ "\n" + "Mix dry ingredients:\n"
				+ "In a separate bowl, vigorously whisk together the flour, cocoa, baking soda, and allspice.\n" + "\n"
				+ "Add the flour mixture to the banana mixture:\n"
				+ "Stir until just incorporated, then stir in the chocolate chips.\n" + "\n" + "Bake:\n"
				+ "Pour batter into a buttered loaf pan. Place in a 350°F (175°C) oven and bake for 1 hour.\n" + "\n"
				+ "Note that because of the chocolate chips that melt in the bread as it cooks, it's hard to check for doneness using a tester that you insert.\n"
				+ "\n"
				+ "One way to test is to gently push the center top of the chocolate banana bread down with your finger. If it feels relatively firm and bounces back when you release your finger, that's a good sign that it's done.\n"
				+ "\n" + "Let cool completely:\n" + "Remove from oven and let cool for 10 minutes in the pan.\n" + "\n"
				+ "Gently remove the loaf from the pan and place on a rack to cool completely.\n" + "\n"
				+ "Slice with a serrated bread knife to serve.");
		breadRecipe.setDifficulty(Difficulty.MEDIUM);
		breadRecipe.addIngredients(new Ingredient("Banana", new BigDecimal(3), large.get()));
		breadRecipe.addIngredients(new Ingredient("Butter", new BigDecimal(1 / 3), cup.get()));
		breadRecipe.addIngredients(new Ingredient("Sugar", new BigDecimal(3 / 4), cup.get()));
		breadRecipe.addIngredients(new Ingredient("Salt", new BigDecimal(1), teaspoon.get()));
		breadRecipe.addIngredients(new Ingredient("Egg Beaten", new BigDecimal(1), large.get()));
		recipeRepository.save(breadRecipe);

	}

	private void prepareBakedOatmealRecipe() {

		Optional<UnitOfMeasure> large = unitOfMeasureRepository.findByUom("Large");
		Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByUom("Cup");
		Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByUom("Teaspoon");

		Optional<Category> easyBaking = categoryRepository.findByDescription("Easy Baking");
		Optional<Category> breakfast = categoryRepository.findByDescription("Breakfast");

		Set<Category> categories = new HashSet<>();
		categories.add(easyBaking.get());
		categories.add(breakfast.get());

		Recipe bakedOatmeal = new Recipe();
		bakedOatmeal.setPrepTime(10);
		bakedOatmeal.setCookTime(45);
		bakedOatmeal.setDescription("Baked Oatmeal with Mixed Berries");
		bakedOatmeal.setServings(6);
		bakedOatmeal.setDirections("Preheat your oven and prepare the baking dish:\n"
				+ "Preheat your oven to 375°F. Grease with butter or lined a 9x9 baking dish with parchment.\n" + "\n"
				+ "A square white ceramic baking dish to make Overhead view of a baking pan with Baked Berry Oatmeal.\n"
				+ "Combine ingredients in a large mixing bowl:\n"
				+ "In a large mixing bowl, add oats, milk, eggs, melted butter, honey, yogurt, vanilla, and salt. Stir with a fork or whisk to combine thoroughly.\n"
				+ "\n" + "Oatmeal mixed in a glass bowl to make Mixed Berry Baked Oatmeal.\n"
				+ "Fold the berries into the mixture and move to casserole dish:\n"
				+ "Fold 3/4 cup each of blueberries and raspberries into the mixture. Pour mixture into prepared casserole dish. Sprinkle all remaining berries over top.\n"
				+ "\n" + "Berries mixed into oatmeal in a glass bowl to make Mixed Berry Baked Oatmeal.\n"
				+ "Mixed Berry Baked Oatmeal in a ceramic baking dish and ready to be baked.\n" + "Bake casserole:\n"
				+ "Bake the oatmeal casserole for 45 minutes, or until the casserole is shiny on top and firm to the touch.");
		bakedOatmeal.setDifficulty(Difficulty.EASY);
		bakedOatmeal.setCategories(categories);
		bakedOatmeal.setSource("SimplyRecipes");
		bakedOatmeal.setUrl("https://www.simplyrecipes.com/baked-oatmeal-with-mixed-berries-recipe-5185886");
		Notes note = new Notes();
		note.setRecipeNotes(
				"This straightforward recipe can be made without any strong culinary skills, and kids can easily help out in the process. Here are some tips to keep in mind when preparing a baked oatmeal casserole.\n"
						+ "\n" + "\n"
						+ "Baked oatmeal has a fairly firm texture, but it should never be dry. Conversely, it should turn out similar to a thick bread pudding. Think creamy and custardy, but firm enough to lift out of the pan with your hands if desired. Just like bread pudding, it should be removed from the oven when barely set, as it will set further while it cools.\n"
						+ "\n" + "\n"
						+ "To not offset the liquid ratio of this dish, fresh berries are the ideal choice. If you need to use frozen berries, be sure to thaw your fruit completely in advance and drain them well. Fold the frozen berries in with the other ingredients gently to make sure they don’t break down too much before baking.");
		bakedOatmeal.setNote(note);
		recipeRepository.save(bakedOatmeal);

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Initial Data Load, To be run only once for persistent DB
		if (unitOfMeasureRepository.count() < 1) {
			loadCategories();
			loadUnitOfMeasure();
			populateBananaBreadRecipe();
			prepareBakedOatmealRecipe();
		}
		log.error("Count of Unit Of Measure:" + reactiveUnitOfMeasureRepository.count().block().toString());
		log.error("Count of Recipe:" + recipeReactiveRepository.count().block().toString());
		log.error("Count of Categories:" + categoryReactiveRepository.count().block().toString());

	}

}
