package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.NotesCommand;
import com.mkrana.recipe.domain.Notes;

class NotesToNotesCommandTest {

	private final String ID = "1";
	private final String RECIPES_NOTES = "RandomNote";
	NotesToNotesCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesToNotesCommand();
	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));

	}

	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new Notes()));

	}

	@Test
	void testConvert() throws Exception {
		Notes command = new Notes();
		command.setId(ID);
		command.setRecipeNotes(RECIPES_NOTES);
		NotesCommand notes = converter.convert(command);
		assertEquals(command.getId(), notes.getId());
		assertEquals(command.getRecipeNotes(), notes.getRecipeNotes());
	}

}
