import { Component, OnInit, Input } from '@angular/core';
import { Note } from '../note';
import { RouterService } from '../services/router.service';
import { MatIconModule } from '@angular/material/icon';
import { NotesService } from '../services/notes.service';
import { CategoryService } from '../services/category.service';
import { Category } from '../create-category/category';
import { UpdateCategoryComponent } from '../update-category/update-category.component';
import { MatDialog } from '@angular/material';
@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  @Input()
  category: Category;
  notes: Array<Note>;
  notesByCategory: Array<Note>;
  constructor(private router: RouterService, private categoryService: CategoryService,
    public dialog: MatDialog, private noteService: NotesService) {
    this.notesByCategory = [];
  }

  ngOnInit() {
  }
  // For deleteCategory
  deleteCategory() {
    this.categoryService.deleteCategory(this.category).subscribe(res => { }
      , err => {
      });
  }
  // For editCategory
  editCategory() {
    this.dialog.open(UpdateCategoryComponent, { data: this.category }).afterClosed().subscribe(result => {
      this.router.routeToNoteView();
    });
  }
  // For Refresh
  getNotesByCategory(value: string) {
    localStorage.setItem('CategoryName', value);
    this.router.routeToListView();
    this.router.routeToViewNotesByCategory();
  }

}
