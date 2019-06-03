package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {
	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperations.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	NoteRepository noteRepository;
	MongoOperations mongoOperations;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository, MongoOperations mongoOperations) {
		this.noteRepository = noteRepository;
		this.mongoOperations = mongoOperations;
	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		boolean isStatus = false;
		NoteUser fetchedNoteUser = new NoteUser();
		List<Note> list = new ArrayList<>();
		try {
			fetchedNoteUser = noteRepository.findById(note.getNoteCreatedBy()).get();// to get noteId
		} catch (Exception e) {
			fetchedNoteUser = null;
		}
		if (fetchedNoteUser != null) {
			list = fetchedNoteUser.getNotes();
			boolean isnoteExists = false;
			for (Note fetchedNote : list) {
				if (note.getNoteId() == fetchedNote.getNoteId()) {
					isnoteExists = true;
					break;
				}
			}
			if (!isnoteExists) {
				// adding existing list
				fetchedNoteUser.getNotes().add(note);
				noteRepository.save(fetchedNoteUser);
				isStatus = true;
			} else {
				isStatus = false;
			}
		} else {// if not exist then add operation
			NoteUser insertedNoteUser = null;
			NoteUser noteUser = new NoteUser();
			List<Note> notes = new ArrayList<>();
			if (note != null) {
				notes.add(note);
				noteUser.setUserId(note.getNoteCreatedBy());
				noteUser.setNotes(notes);
				try {
					insertedNoteUser = noteRepository.insert(noteUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
			if (insertedNoteUser != null) {
				isStatus = true;
			}
		}
		return isStatus;
	}

	/* This method should be used to delete an existing note. */
	public boolean deleteNote(String userId, int noteId) {
		boolean isStatus = false;
		List<Note> list = new ArrayList<>();
		NoteUser fetchedNoteUser = new NoteUser();
		if (userId != null && noteId != -1) {
			try {
				fetchedNoteUser = noteRepository.findById(userId).get();
			} catch (NoSuchElementException e) {
				fetchedNoteUser = null;
			}
			if (fetchedNoteUser != null) {
				list = fetchedNoteUser.getNotes();
				int noteIndex = 0;
				boolean isNoteExists = false;
				for (Note note : list) {
					if (noteId == note.getNoteId()) {
						isNoteExists = true;
						break;
					}
					noteIndex++;
				}
				if (isNoteExists && list.size() > 1) {
					fetchedNoteUser.getNotes().remove(noteIndex);
					noteRepository.save(fetchedNoteUser);// For +ve case, save action
					isStatus = true;
				} else {
					noteRepository.delete(fetchedNoteUser);// For -ve case, delete action
					isStatus = true;
				}
			}
		}
		return isStatus;
	}

	/* This method should be used to delete all notes with specific userId. */
	public boolean deleteAllNotes(String userId) {
		boolean isStatus = false;
		NoteUser fetchedNoteUser = new NoteUser();
		if (userId != null) {
			try {
				fetchedNoteUser = noteRepository.findById(userId).get();
			} catch (NoSuchElementException e) {
				fetchedNoteUser = null;
			}
			if (fetchedNoteUser != null) {
				noteRepository.delete(fetchedNoteUser);// delete
				isStatus = true;
			}
		}
		return isStatus;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		Note resultnote = new Note();
		NoteUser fetchedNoteUser = new NoteUser();
		List<Note> list = new ArrayList<>();
		if (userId != null && id != 0 && note != null) {
			try {
				fetchedNoteUser = noteRepository.findById(userId).get();
			} catch (NoSuchElementException e) {
				fetchedNoteUser = null;
			}
			if (fetchedNoteUser != null) {
				list = fetchedNoteUser.getNotes();
				int index = 0;
				for (Note fetchedNote : list) {
					if (id == fetchedNote.getNoteId()) {
						note.setNoteId(fetchedNote.getNoteId());
						note.setNoteCreatedBy(fetchedNote.getNoteCreatedBy());
						note.setNoteCreationDate(fetchedNote.getNoteCreationDate());
						break;
					}
					index++;
				}
				list.remove(index);
				list.add(note);
				fetchedNoteUser.setNotes(list);
				noteRepository.save(fetchedNoteUser);
				resultnote = note;
			} else {
				throw new NoteNotFoundExeption("Note Not Found");
			}
		}
		return resultnote;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		Note resultnote = new Note();
		List<Note> list = new ArrayList<>();
		NoteUser fetchedNoteUser = new NoteUser();
		if (userId != null && noteId != 0) {
			try {
				fetchedNoteUser = noteRepository.findById(userId).get();
			} catch (NoSuchElementException e) {
				fetchedNoteUser = null;
			}
			if (fetchedNoteUser != null) {
				list = fetchedNoteUser.getNotes();
				for (Note note : list) {
					if (noteId == note.getNoteId()) {
						resultnote = note;
					}
				}
			} else {
				throw new NoteNotFoundExeption("Note Not Found");
			}
		}
		return resultnote;
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		List<Note> list = new ArrayList<>();
		NoteUser fetchedNoteUser = new NoteUser();
		if (userId != null) {
			try {
				fetchedNoteUser = noteRepository.findById(userId).get();
			} catch (NoSuchElementException e) {
				fetchedNoteUser = null;
			}
			if (fetchedNoteUser != null) {
				list = fetchedNoteUser.getNotes();
			}
		}
		return list;
	}
}