import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-category-notes',
  templateUrl: './category-notes.component.html',
  styleUrls: ['./category-notes.component.css']
})
export class CategoryNotesComponent implements OnInit {

  notes: Array<Note>;
  categoryName: string;
  notesByCategory: Array<Note>;

  constructor(private noteService: NotesService, private authService: AuthenticationService, public routerService: RouterService) {
    this.notesByCategory = [];
  }
  // Add notes by category based
  ngOnInit() {
    if (localStorage.getItem('CategoryName') !== null) {
      this.noteService.getNotes().subscribe(noteList => {
        this.categoryName = localStorage.getItem('CategoryName');
        for (const note of noteList) {
          if (note.category !== null) {
            if (note.category.categoryName === this.categoryName) {
              this.notesByCategory.push(note);
            }
          }
        }
        this.notes = this.notesByCategory;
      });
    }
    localStorage.removeItem('CategoryName');
  }
}
