import { Component, OnInit, ViewChild } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { Category } from '../create-category/category';
import { CategoryService } from '../services/category.service';
import { Reminder } from '../create-reminder/reminder';
import { ReminderService } from '../services/reminder.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent implements OnInit {

  errMessage: string;
  notes: Note[];
  note: Note;
  category: Category;
  reminder: Reminder;
  private categoriesList: Array<Category>;
  private remindersforView: Array<Reminder>;
  private selectedReminders: Array<String>;
  private remindersForNote: Array<Reminder>;

  constructor(private noteService: NotesService, private categoryService: CategoryService, private reminderService: ReminderService) {
    this.note = new Note();
    this.category = new Category();
    this.reminder = new Reminder();
    this.notes = [];
    this.selectedReminders = [];
    this.remindersForNote = [];
  }

  ngOnInit() {
    this.noteService.getNotes().subscribe(notesRepsonseList => {
      this.notes = notesRepsonseList;
    }, error => {
      this.errMessage = error.message;
    });

    this.categoryService.fetchCategoriesFromServer();
    this.categoryService.getCategories().subscribe(categories => {
      this.categoriesList = categories;
    });

    this.reminderService.fetchRemindersFromServer();
    this.reminderService.getReminders().subscribe(reminder => {
      this.remindersforView = reminder;
    });

  }
  // Adding notes to Mongo DB when click on Done button
  takeNote() {
    if (this.category.id !== undefined) {
      this.category = this.categoryService.getCategoryFromId(this.category.id);
      this.category.categoryId = this.category.id;
      this.note.category = this.category;
    }
    if (this.selectedReminders.length > 0) {
      for (const reminderId of this.selectedReminders) {
        this.reminder = this.reminderService.getReminderFromId(reminderId);
        this.remindersForNote.push(this.reminder);
        this.note.reminders = this.remindersForNote;
      }
    }
    // Adding empty validation when click on Done button
    if (this.note.noteTitle === '' && this.note.noteContent === '') {
      this.errMessage = 'Title and Text both are required fields';
    }
    // To check whether user entered text or not and allow user to create note
    if (this.note.noteTitle !== '' && this.note.noteContent !== '') {
      this.errMessage = ''; // This all clear the error message when new note added successfully
      this.note.noteStatus = 'not-started';

      this.noteService.addNote(this.note).subscribe(addedNote => {
        this.remindersForNote = [];
      }, error => { // adding note will be removed dynamically from Note View while submit the input data as wrong
        this.errMessage = error.message;
        const index = this.notes.findIndex(note => note.noteId === this.note.noteId);
        this.notes.splice(index, 1);
      });
      this.note = new Note();
      this.note.noteTitle = ''; // This all clear the fields when new note added successfully
      this.note.noteContent = '';
    }
  }
}
