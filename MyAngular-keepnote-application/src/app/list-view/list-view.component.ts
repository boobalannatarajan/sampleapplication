import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.css']
})
export class ListViewComponent implements OnInit {

  notStartedNotes: Array<Note>;
  startedNotes: Array<Note>;
  completedNotes: Array<Note>;
  notes: Array<Note>;

  constructor(private noteService: NotesService) { }

  ngOnInit() {
    this.noteService.getNotes().subscribe(data => {
      this.notes = data;
      this.filteredNotes();
    });
  }
  // Filter to get Selected Array List based on state in DB
  filteredNotes() {
    this.notStartedNotes = this.notes.filter(note => note.noteStatus === 'not-started');
    this.startedNotes = this.notes.filter(note => note.noteStatus === 'started');
    this.completedNotes = this.notes.filter(note => note.noteStatus === 'completed');
  }
}
