package com.mkrana.recipe.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mkrana.recipe.command.NotesCommand;
import com.mkrana.recipe.domain.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Nullable
	@Override
	public Notes convert(NotesCommand source) {

		if (source == null) {
			return null;
		}

		Notes notes = new Notes();
		notes.setId(source.getId());
		notes.setRecipeNotes(source.getRecipeNotes());
		return notes;
	}

}
