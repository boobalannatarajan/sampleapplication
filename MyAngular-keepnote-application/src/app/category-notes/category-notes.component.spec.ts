import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryNotesComponent } from './category-notes.component';

describe('CategoryNotesComponent', () => {
  let component: CategoryNotesComponent;
  let fixture: ComponentFixture<CategoryNotesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryNotesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
