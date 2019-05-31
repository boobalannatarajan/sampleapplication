import { LoginPage } from './page-objects/login.po';
import { browser, by, element, ElementFinder, promise, protractor } from 'protractor';
import { NoteViewPage } from './page-objects/note.po';

describe('Logout Functionality:', () => {
    let page: LoginPage;
    let note: NoteViewPage;

    beforeEach(() => {
      page = new LoginPage();
      note = new NoteViewPage();
    });
    // should logout, redirect to login page
    it('Logout and then navigation to login page', () => {
        note.navigateToNoteView();
        element(by.css('button[type=logout]')).click();
        expect(page.getCurrentURL()).toContain('login');
  });
});


