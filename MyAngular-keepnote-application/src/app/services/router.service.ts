import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Injectable()
export class RouterService {
  constructor(public router: Router,
    private location: Location) { }
  // Routing to Dashboard
  routeToDashboard() {
    this.router.navigate(['dashboard']);
  }
  // Routing to login
  routeToLogin() {
    this.router.navigate(['login']);
  }
  // Navigation route to EditNote
  routeToEditNoteView(noteId) {
    this.router.navigate(['dashboard', {
      outlets: {
        noteEditOutlet: ['note', noteId, 'edit']
      }
    }]);
  }
  // For Stay back notes
  routeBack() {
    this.location.back();
  }
  // For List View
  routeToListView() {
    this.router.navigate(['dashboard/view/listview']);
  }
  // For Note View
  routeToNoteView() {
    this.router.navigate(['dashboard/view/noteview']);
  }
  // Routing to Registration page
  routeToSignUp() {
    this.router.navigate(['signup']);
  }
  // To  ViewNotes
  routeToViewNotes() {
    this.router.navigate(['viewnotes']);
  }
  // To  ViewNReminders
  routeToViewReminders() {
    this.router.navigate(['viewNotesReminder']);
  }
  // To  ViewCategory
  routeToViewNotesByCategory() {
    this.router.navigate(['viewnotesbycategory']);
  }
}
