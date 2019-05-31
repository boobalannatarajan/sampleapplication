import { Injectable } from '@angular/core';
import { Category } from '../create-category/category';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './authentication.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable()
export class CategoryService {
  category: Category;
  categories: Array<Category>;
  categoriesSubject: BehaviorSubject<Array<Category>>;

  constructor(private httpClient: HttpClient, private authService: AuthenticationService) {
    this.categories = [];
    this.categoriesSubject = new BehaviorSubject([]);
    this.category = new Category();
  }
  // To get all category list from mongo DB
  fetchCategoriesFromServer() {
    if (this.authService.getUserId() !== null) {
      this.httpClient.get<Category[]>(`http://localhost:8080/api/v1/category/user/${this.authService.getUserId()}`, {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      }).subscribe(categoriesResponse => {
        this.categories = categoriesResponse;
        this.categoriesSubject.next(this.categories);
      });
    } else {
    }

  }

  getCategories(): BehaviorSubject<Array<Category>> {
    return this.categoriesSubject;
  }
  // Get category based on Id
  getCategoryFromId(categoryId: String): Category {
    return this.categories.find(category => category.id === categoryId);
  }
  // Add category to DB
  addCategory(category: Category): Observable<Category> {
    category.categoryCreatedBy = this.authService.getUserId();
    return this.httpClient.post<Category>('http://localhost:8080/api/v1/category', category, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).do(addCategories => {
      this.categories.push(addCategories);
      this.categoriesSubject.next(this.categories);
    });
  }
  // Delete category from DB
  deleteCategory(category: Category) {
    return this.httpClient.delete(`http://localhost:8080/api/v1/category/${category.id}`,
      {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      }).do(deleteCategory => {
        const index = this.categories.findIndex(deleCategory => deleCategory.id === category.id);
        this.categories.splice(index, 1);
        this.categoriesSubject.next(this.categories);
      });
  }

  getCategoryById(category: Category) {
    return this.httpClient.get<Category>(`http://localhost:8080/api/v1/category/${category.id}`,
      {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      });
  }
  // Update category to DB
  editCategory(category: Category): Observable<Category> {
    return this.httpClient.put<Category>(`http://localhost:8080/api/v1/category/${category.id}`, category, {
      headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
    }).pipe(
      tap(editedCategory => {
        const categoryObj = this.categories.find(data => data.id === editedCategory.id);
        Object.assign(categoryObj, editedCategory);
        this.categoriesSubject.next(this.categories);
      })
      );
  }

}
