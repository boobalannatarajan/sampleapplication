import { Component, ViewChild } from '@angular/core';
import { NotesService } from '../services/notes.service';
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private notesService: NotesService, private authService: AuthenticationService, public routerService: RouterService) {
    this.notesService.fetchNotesFromServer();

}
  ngOnInit() {
    this.notesService.fetchNotesFromServer(); // To fetch all added noted from db on time while loading page
  }
}
