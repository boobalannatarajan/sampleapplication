import { Component, OnInit, Input } from '@angular/core';
import { Note } from '../note';
import { RouterService } from '../services/router.service';
import { MatIconModule } from '@angular/material/icon';
import { NotesService } from '../services/notes.service';
import { ReminderService } from '../services/reminder.service';
import { Reminder } from '../create-reminder/reminder';
import { UpdateCategoryComponent } from '../update-category/update-category.component';
import { MatDialog } from '@angular/material';
import { UpdateReminderComponent } from '../update-reminder/update-reminder.component';

@Component({
  selector: 'app-reminder',
  templateUrl: './reminder.component.html',
  styleUrls: ['./reminder.component.css']
})
export class ReminderComponent implements OnInit {

  @Input()
  reminder: Reminder;
  notes: Array<Note>;
  notesByCategory: Array<Note>;
  constructor(private router: RouterService,
    private reminderService: ReminderService,
    public dialog: MatDialog, private noteService: NotesService) {
    this.notesByCategory = [];
  }

  ngOnInit() {
  }

  // For delete reminder click on delete icon
  deleteReminder() {
    this.reminderService.deleteReminder(this.reminder).subscribe(res => { }
      , err => {
      });
  }
  // For edit reminder click on edit icon
  editReminder() {
    this.dialog.open(UpdateReminderComponent, { data: this.reminder }).afterClosed().subscribe(result => {
      this.router.routeToNoteView();
    });
  }

}
