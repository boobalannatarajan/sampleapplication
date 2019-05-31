import { Component, OnInit } from '@angular/core';
import { RouterService } from './services/router.service';
import { MatDialog } from '@angular/material';
import { CreateCategoryComponent } from './create-category/create-category.component';
import { AuthenticationService } from './services/authentication.service';
import { Category } from './create-category/category';
import { CategoryService } from './services/category.service';
import { ReminderService } from './services/reminder.service';
import { CreateReminderComponent } from './create-reminder/create-reminder.component';
import { Reminder } from './create-reminder/reminder';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  events: string[] = [];
  opened: boolean;
  categories: Array<Category>;
  reminders: Array<Reminder>;
  constructor(public routerService: RouterService, public dialog: MatDialog, private authService: AuthenticationService,
    private categoryService: CategoryService, private reminderService: ReminderService) {
  }
  // To on load both reminder and category
  ngOnInit() {
    this.loadCategories();
    this.loadReminders();
  }
  // Get notes
  getNotesView() {
    this.routerService.routeToNoteView();
  }
  // For view notes
  getViewNotes() {
    this.routerService.routeToViewNotes();
  }
  // Get Reminder and view
  getNotesReminderView() {
    this.routerService.routeToViewReminders();
  }
  // Add category to DB
  addCategory() {
    if (this.authService.getBearerToken() !== null) {
      this.authService.isUserValid(this.authService.getBearerToken()).subscribe(res => {
        if (res['isAuthenticated'] === 'true') { // For postive case check and then note view
          this.dialog.open(CreateCategoryComponent, {}).afterClosed().subscribe(result => {
            this.routerService.routeToNoteView();
          });
        } else { // for negative, route to login
          this.routerService.routeToLogin();
        }
      },
        err => {
          this.routerService.routeToLogin(); // for negative, route to login
        });
    } else {
      this.routerService.routeToLogin(); // for negative, route to login
    }
  }
  // For Refresh category
  loadCategories() {
    this.categoryService.fetchCategoriesFromServer();
    this.categoryService.getCategories().subscribe(category => {
      this.categories = category;
    });
  }
  // For Refresh reminder
  loadReminders() {
    this.reminderService.fetchRemindersFromServer();
    this.reminderService.getReminders().subscribe(reminder => {
      this.reminders = reminder;
    });
  }
  // For add reminder
  addReminder() {
    if (this.authService.getBearerToken() !== null) {
      this.authService.isUserValid(this.authService.getBearerToken()).subscribe(res => {
        if (res['isAuthenticated'] === 'true') { // For postive case check and then note view
          this.dialog.open(CreateReminderComponent, {}).afterClosed().subscribe(result => {
            this.routerService.routeToNoteView();
          });
        } else {
          this.routerService.routeToLogin(); // for negative, route to login
        }
      },
        err => {
          this.routerService.routeToLogin(); // for negative, route to login
        });
    } else {
      this.routerService.routeToLogin(); // for negative, route to login
    }
  }

}
