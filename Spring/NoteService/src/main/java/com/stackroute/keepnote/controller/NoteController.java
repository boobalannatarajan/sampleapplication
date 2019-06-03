package com.stackroute.keepnote.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@Api
@CrossOrigin(origins = "*") // Means to that get URL access with no address specification
public class NoteController {
	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	NoteService noteService;

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/*
	 * Swagger
	 */
	@GetMapping("/")
	public String swaggerUi() {
		return "redirect:/swagger-ui.html";
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the note created
	 * successfully. 2. 409(CONFLICT) - If the noteId conflicts with any existing
	 * user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST
	 * method
	 */
	@ApiOperation(value = "Create Note")
	@PostMapping("/api/v1/note")
	public ResponseEntity<Note> createNote(@RequestBody Note note) {
		ResponseEntity<Note> output = null;
		boolean isBooStatus = false;
		int noteId = (int) (Math.random() * 10000);
		note.setNoteId(noteId);
		isBooStatus = noteService.createNote(note);
		if (isBooStatus) {
			output = new ResponseEntity<Note>(note, HttpStatus.CREATED); // For 201
		} else {
			output = new ResponseEntity<Note>(HttpStatus.CONFLICT);// for 409
		}
		return output;
	}

	/*
	 * Define a handler method which will delete a note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}"
	 * using HTTP Delete method" where "id" should be replaced by a valid noteId
	 * without {}
	 */
	@ApiOperation(value = "Delete Note By Note Id")
	@DeleteMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<Note> deleteNoteByNoteId(@PathVariable("userId") String userId,
			@PathVariable("noteId") int noteId) {
		Note noteObj = new Note();
		noteObj.setNoteId(noteId);
		noteObj.setNoteCreatedBy(userId);
		boolean isBooStatus = false;
		isBooStatus = noteService.deleteNote(userId, noteId);
		if (isBooStatus) {
			return new ResponseEntity<Note>(noteObj, HttpStatus.OK); // For 200
		} else {
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND); // For 404
		}
	}

	/*
	 * Define a handler method which will delete all note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{userId}" using HTTP
	 * Delete method" where "id" should be replaced by a valid noteId without {}
	 */
	@ApiOperation(value = "Delete Note By User Id")
	@DeleteMapping("/api/v1/note/{userId}")
	public ResponseEntity<Note> deleteAllNote(@PathVariable("userId") String userId) {
		boolean isStatus = false;
		try {
			isStatus = noteService.deleteAllNotes(userId); // Delete based on input id
		} catch (NoteNotFoundExeption e) {
		}
		if (isStatus) {
			return new ResponseEntity<Note>(HttpStatus.OK);// 200
		} else {
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);// 404
		}
	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}"
	 * using HTTP PUT method.
	 */
	@ApiOperation(value = "Update Note")
	@PutMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<Note> updateNote(@PathVariable("userId") String userId, @PathVariable("noteId") int noteId,
			@RequestBody Note note) {
		Note resultNote = new Note();
		try {
			resultNote = noteService.updateNote(note, noteId, userId);// updation based on userid & noteID
		} catch (NoteNotFoundExeption e) {
			resultNote = null;
		}
		if (resultNote != null) {
			return new ResponseEntity<Note>(resultNote, HttpStatus.OK);
		} else {
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will get us the all notes by a userId. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{userId}" using HTTP
	 * GET method
	 */
	@ApiOperation(value = "Get All Notes By User Id")
	@GetMapping("/api/v1/note/{userId}")
	public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable("userId") String userId) {
		ResponseEntity<List<Note>> outpt = null;
		List<Note> noteObj = null;
		noteObj = noteService.getAllNoteByUserId(userId);// Retrive the noted based on ip userId
		outpt = new ResponseEntity<List<Note>>(noteObj, HttpStatus.OK);
		return outpt;
	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@ApiOperation(value = "Get Notee By Note Id")
	@GetMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<Note> getNoteByNoteId(@PathVariable("userId") String userId,
			@PathVariable("noteId") int noteId) {
		Note note = null;
		try {
			note = noteService.getNoteByNoteId(userId, noteId);
		} catch (NoteNotFoundExeption e) {
		}
		if (note == null) {
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);// For -ve case
		} else {
			return new ResponseEntity<Note>(note, HttpStatus.OK);// For + ve case
		}
	}
}