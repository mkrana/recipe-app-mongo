package com.mkrana.recipe.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mkrana.recipe.command.NotesCommand;
import com.mkrana.recipe.domain.Notes;

class NotesCommandToNotesTest {

	private final String ID = "1";
	private final String RECIPES_NOTES = "RandomNote";
	NotesCommandToNotes converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesCommandToNotes();
	}

	@Test
	void testNullParameter() {
		assertNull(converter.convert(null));

	}

	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new NotesCommand()));

	}

	@Test
	void testConvert() throws Exception {
		NotesCommand command = new NotesCommand();
		command.setId(ID);
		command.setRecipeNotes(RECIPES_NOTES);
		Notes notes = converter.convert(command);
		assertEquals(command.getId(), notes.getId());
		assertEquals(command.getRecipeNotes(), notes.getRecipeNotes());
	}

}
