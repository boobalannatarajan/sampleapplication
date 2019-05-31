import { Injectable } from '@angular/core';
import { Note } from '../note';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './authentication.service';
import { tap } from 'rxjs/operators';
import 'rxjs/add/operator/do';

@Injectable() // Dependency injection
export class NotesService {

  notes: Array<Note>;
  notesSubject: BehaviorSubject<Array<Note>>;

  constructor(private httpClient: HttpClient, private authService: AuthenticationService) {
    this.notes = [];
    this.notesSubject = new BehaviorSubject([]);
  }
  // Get notes from DB on URL basics
  fetchNotesFromServer() {
    this.httpClient.get<Note[]>(`http://localhost:8081/api/v1/note/${this.authService.getUserId()}`, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).subscribe(notesResponse => {
      this.notes = notesResponse;
      this.notesSubject.next(this.notes);
    }, err => { // Session expired alert message
      if (err.message === 'Http failure response for (unknown url): 0 Unknown Error') {
        alert(' Hi ' + this.authService.getUserId() + ' - Your Valid Session has expired now, Please do logout and then continue...');
      }
    });
  }

  getNotes(): BehaviorSubject<Array<Note>> {
    return this.notesSubject;
  }
  // To add note to DB
  addNote(note: Note): Observable<Note> {
    note.noteCreatedBy = this.authService.getUserId();
    return this.httpClient.post<Note>('http://localhost:8081/api/v1/note', note, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).do(addNotes => { // adding behaviour subject while in addnotes
      this.notes.push(addNotes);
      this.notesSubject.next(this.notes);
    });
  }
  // update note to DB
  editNote(note: Note): Observable<Note> {
    return this.httpClient.put<Note>(`http://localhost:8081/api/v1/note/${this.authService.getUserId()}/${note.noteId}`, note, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).pipe(
      tap(editeNote => {
        const noteobj = this.notes.find(data => data.noteId === editeNote.noteId);
        Object.assign(noteobj, editeNote);
        this.notesSubject.next(this.notes);
      })
      );
  }
  // For solving Eslint  === issues when dialog box edits
  getNoteById(noteId: number): Note {
    return this.notes.find(note => note.noteId === noteId);
  }
  /*
  getNoteById(noteId: number): Note {
    const foundNote = this.notes.find(note => note.id === noteId);
    return Object.assign({}, foundNote);
  }*/
  // For delete notes from mongo DB
  deleteNote(noteId) {
    return this.httpClient.delete(`http://localhost:8081/api/v1/note/${this.authService.getUserId()}/${noteId}`,
      {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      }).do(deleteNote => {
        const index = this.notes.findIndex(deletNote => deletNote.noteId === noteId);
        this.notes.splice(index, 1);
        this.notesSubject.next(this.notes);
      });
  }
}
