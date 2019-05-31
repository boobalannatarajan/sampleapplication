import { LoginPage } from './page-objects/login.po';
import { browser, by, element, ElementFinder, promise, protractor } from 'protractor';
import { NoteViewPage } from './page-objects/note.po';

describe('category functionality:', () => {
    let page: LoginPage;
    let note: NoteViewPage;
    beforeEach(() => {
        page = new LoginPage();
        note = new NoteViewPage();
    });
   // Create New Category
    it('Create New Category', () => {
        element(by.css('button[type=menuslide]')).click();
        element(by.css('button[type=newcategory]')).click();
        element(by.id('categoryname')).sendKeys('c1');
        element(by.id('categorydesc')).sendKeys('CategoryDesc');
        element(by.css('button[type=savecategory]')).click();
    });
    // Update existing category
    it('Update existing category', () => {
        element(by.className('categoryrefresh')).click();
        const categoryElement = element.all(by.className('editcategory'));
        categoryElement.get(0).click();
        element(by.id('updCategoryName')).sendKeys('c2');
        element(by.id('updCategoryDesc')).sendKeys('Category two');
        element(by.css('button[type=updateCategory]')).click();
    });
    // Delete existing category
    it('Delete existing Category', () => {
        element(by.className('categoryrefresh')).click();
        const categoryElement = element.all(by.css('button[type=categorydelete]'));
        categoryElement.get(0).click();
    });

});

