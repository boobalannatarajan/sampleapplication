import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Note } from '../note';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NotesService } from '../services/notes.service';
import { error } from 'util';
import { RouterService } from '../services/router.service';
import { Category } from '../create-category/category';
import { CategoryService } from '../services/category.service';
import { Reminder } from '../create-reminder/reminder';
import { ReminderService } from '../services/reminder.service';

@Component({
  selector: 'app-edit-note-view',
  templateUrl: './edit-note-view.component.html',
  styleUrls: ['./edit-note-view.component.css']
})
export class EditNoteViewComponent implements OnDestroy {
  note: Note;
  states: Array<string> = ['not-started', 'started', 'completed'];
  errMessage: string;
  category: Category;
  reminder: Reminder;
  private categoriesList: Array<Category>;
  private remindersforView: Array<Reminder>;
  private selectedReminders: Array<String>;
  private remindersForNote: Array<Reminder>;

  constructor(public dialogRef: MatDialogRef<EditNoteViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notesService: NotesService,
    private routes: RouterService, private categoryService: CategoryService,
    private noteService: NotesService, private reminderService: ReminderService) {
    this.selectedReminders = [];
    this.reminder = new Reminder();
    this.remindersForNote = [];

    // Get category list
    this.categoryService.getCategories().subscribe(categories => {
      this.categoriesList = categories;
    });


    this.note = this.data;
    this.category = this.note.category;

    // Get reminder list
    this.reminderService.getReminders().subscribe(reminder => {
      this.remindersforView = reminder;
    });
    // add reminder to DB based on note
    if (this.note.reminders !== null) {
      if (this.note.reminders.length > 0) {
        for (const rem of this.note.reminders) {
          this.selectedReminders.push(rem.reminderName);
        }
      }
    }

  }
  // click on save , it will add to Mongo DB and refresh the dashboard with latest addednote
  onSave() {
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

    let editedNote: Note;
    editedNote = new Note();
    editedNote.noteId = this.note.noteId;
    editedNote.noteTitle = this.note.noteTitle;
    editedNote.noteContent = this.note.noteContent;
    editedNote.noteStatus = this.note.noteStatus;
    editedNote.category = this.note.category;
    editedNote.reminders = this.note.reminders;
    this.notesService.editNote(editedNote).subscribe(res => {
    }, err => {
      this.errMessage = err.message;
    });

    this.dialogRef.close(); // For making dialog box close , when click on out of dialog box
  }
  ngOnDestroy() {
    this.routes.routeBack(); // It will route to routerservice for stay back notes
    this.noteService.fetchNotesFromServer();
  }
}
