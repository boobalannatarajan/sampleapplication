import { NgModule } from '@angular/core';

import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { AppComponent } from './app.component';
import { NoteTakerComponent } from './note-taker/note-taker.component';
import { NoteViewComponent } from './note-view/note-view.component';
import { NoteComponent } from './note/note.component';
import { ListViewComponent } from './list-view/list-view.component';
import { EditNoteOpenerComponent } from './edit-note-opener/edit-note-opener.component';
import { EditNoteViewComponent } from './edit-note-view/edit-note-view.component';
import { MatToolbarModule, MatSidenavModule, MatIconModule, MatListModule} from '@angular/material';
import {MatCardModule} from '@angular/material/card';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';

import { BrowserModule } from '@angular/platform-browser';
import { FormsModule} from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import {MatSelectModule} from '@angular/material/select';
import {MatDialogModule} from '@angular/material/dialog';
import { MatDialog, MatCheckboxModule } from '@angular/material';

import { RouterModule, Routes } from '@angular/router';
import { CanActivateRouteGuard } from './can-activate-route.guard';
import { AuthenticationService } from './services/authentication.service';
import { RouterService } from './services/router.service';
import { NotesService } from './services/notes.service';
import { UserService } from './services/user.service';
import { CategoryService } from './services/category.service';
import { ReminderService } from './services/reminder.service';
import { LayoutModule } from '@angular/cdk/layout';
import {ReactiveFormsModule} from '@angular/forms';
import { SignupComponent } from './signup/signup.component';
import { CreateCategoryComponent } from './create-category/create-category.component';
import { CategoryComponent } from './category/category.component';
import { UpdateCategoryComponent } from './update-category/update-category.component';
import { CategoryNotesComponent } from './category-notes/category-notes.component';
import { CreateReminderComponent } from './create-reminder/create-reminder.component';
import { ReminderComponent } from './reminder/reminder.component';
import { UpdateReminderComponent } from './update-reminder/update-reminder.component';
import { ReminderNotesComponent } from './reminder-notes/reminder-notes.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }, // empty URL path which routes to dashboard page
  { path: 'login', component: LoginComponent }, // URL path of login page
  { path: 'signup', component: SignupComponent},
  { path: 'viewnotesbycategory', component: CategoryNotesComponent, canActivate: [CanActivateRouteGuard]},
  { path: 'viewNotesReminder', component: ReminderNotesComponent, canActivate: [CanActivateRouteGuard]},
  { path: 'dashboard', component: DashboardComponent, canActivate: [CanActivateRouteGuard],
  children: [
    {
      path: 'view/noteview', component: NoteViewComponent // URL path of noteview
    },
    {
      path: 'view/listview', component: ListViewComponent // URL path of Listview
    },
    {
      path: 'note/:noteid/edit', component: EditNoteOpenerComponent, // URL path of edit based on id
      outlet: 'noteEditOutlet'
    },
    {
      path: '', redirectTo: 'view/noteview', pathMatch: 'full' // URL path of noteview through dashboard URL
    }
  ]
 }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    DashboardComponent,
    NoteTakerComponent,
    NoteViewComponent,
    NoteComponent,
    EditNoteOpenerComponent,
    EditNoteViewComponent,
    ListViewComponent,
    SignupComponent,
    CreateCategoryComponent,
    CategoryComponent,
    UpdateCategoryComponent,
    CategoryNotesComponent,
    CreateReminderComponent,
    ReminderComponent,
    UpdateReminderComponent,
    ReminderNotesComponent
   ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSelectModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatCheckboxModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    NotesService,
    AuthenticationService,
    CanActivateRouteGuard,
    RouterService,
    MatDialog,
    UserService,
    CategoryService,
    ReminderService
  ],
  bootstrap: [AppComponent],
  entryComponents: [EditNoteViewComponent, CreateCategoryComponent,
    UpdateCategoryComponent, CreateReminderComponent, UpdateReminderComponent ]
})

export class AppModule { }
