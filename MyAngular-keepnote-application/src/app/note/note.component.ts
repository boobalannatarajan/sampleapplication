import { Component, OnInit, Input } from '@angular/core';
import { Note } from '../note';
import { RouterService } from '../services/router.service';
import { MatIconModule } from '@angular/material/icon';
import { NotesService } from '../services/notes.service';
import { Category } from '../create-category/category';
import { Reminder } from '../create-reminder/reminder';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit {

  @Input()
  note: Note;
  category: Category;
  reminderNames: Array<String>;
  reminder: Reminder;
  constructor(private router: RouterService, private noteService: NotesService) {
    this.reminderNames = [];
    this.reminder = new Reminder();
  }

  ngOnInit() {
    this.category = this.note.category;
    if (this.category === null) {
      this.category = new Category();
    }

    if (this.note.reminders !== null) {
      if (this.note.reminders.length > 0) {
        for (const rem of this.note.reminders) {
          this.reminderNames.push(rem.reminderName);
        }
      }
    }

  }

  routeToEditNoteView() {
    // open the view for edit by routing to edit opener
    this.router.routeToEditNoteView(this.note.noteId);

  }
  // For delete note action
  deleteNote() {
    this.noteService.deleteNote(this.note.noteId).subscribe(res => { }
      , err => {
      });
  }
}
