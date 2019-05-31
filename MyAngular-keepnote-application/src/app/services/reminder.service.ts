import { Injectable } from '@angular/core';
import { Reminder } from '../create-reminder/reminder';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './authentication.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable()
export class ReminderService {
  reminder: Reminder;
  reminders: Array<Reminder>;
  remindersSubject: BehaviorSubject<Array<Reminder>>;

  constructor(private httpClient: HttpClient, private authService: AuthenticationService) {
    this.reminders = [];
    this.remindersSubject = new BehaviorSubject([]);
    this.reminder = new Reminder();
  }
  // Get reminder list from DB
  fetchRemindersFromServer() {
    if (this.authService.getUserId() !== null) {
      this.httpClient.get<Reminder[]>(`http://localhost:8082/api/v1/reminder`, {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      }).subscribe(remindersResponse => {
        this.reminders = remindersResponse;
        this.remindersSubject.next(this.reminders);
      });
    } else {
    }
  }
  // Behavior sub for reminder list
  getReminders(): BehaviorSubject<Array<Reminder>> {
    return this.remindersSubject;
  }
  // Add to DB
  addReminder(reminder: Reminder): Observable<Reminder> {
    reminder.reminderCreatedBy = this.authService.getUserId();
    return this.httpClient.post<Reminder>('http://localhost:8082/api/v1/reminder', reminder, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).do(addReminders => {
      this.reminders.push(addReminders);
      this.remindersSubject.next(this.reminders);
    });
  }
  // Delete reminder from DB
  deleteReminder(reminder: Reminder) {
    return this.httpClient.delete(`http://localhost:8082/api/v1/reminder/${reminder.reminderId}`,
      {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      }).do(deleteReminder => {
        const index = this.reminders.findIndex(deleteRemier => deleteRemier.id === reminder.reminderId);
        this.reminders.splice(index, 1);
        this.remindersSubject.next(this.reminders);
      });
  }
  // Get reminder based on ID
  getReminderById(reminder: Reminder) {
    return this.httpClient.get<Reminder>(`http://localhost:8082/api/v1/reminder/${reminder.id}`,
      {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      });
  }

  getReminderFromId(reminderId: String): Reminder {
    return this.reminders.find(reminder => reminder.reminderId === reminderId);
  }
  // update reminder to DB
  editReminder(reminder: Reminder): Observable<Reminder> {
    return this.httpClient.put<Reminder>(`http://localhost:8082/api/v1/reminder/${reminder.reminderId}`, reminder, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).pipe(
      tap(editedReminder => {
        const reminderObj = this.reminders.find(data => data.id === editedReminder.reminderId);
        Object.assign(reminderObj, editedReminder);
        this.remindersSubject.next(this.reminders);
      })
      );
  }

}
