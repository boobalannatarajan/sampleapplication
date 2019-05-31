
import { LoginPage } from './page-objects/login.po';
import { browser, by, element, ElementFinder, promise, protractor } from 'protractor';
import { NoteViewPage } from './page-objects/note.po';

describe('Reminder Functionality:', () => {
    let page: LoginPage;
    let note: NoteViewPage;
    beforeEach(() => {
        page = new LoginPage();
        note = new NoteViewPage();
    });
    // Add New Reminder
    it('Add New Reminder', () => {
       // element(by.css('button[type=menuslide]')).click();
        element(by.css('button[type=newreminder]')).click();
        element(by.id('remindername')).sendKeys('R1');
        element(by.id('reminderdesc')).sendKeys('Reminder one');
        element(by.id('remindertype')).sendKeys('Daily');
        element(by.css('button[type=remindersubmit]')).click();
    });
    // Update existing Reminder
    it('Update existing Reminder', () => {
        element(by.className('reminderrefresh')).click();
        const reminderElement = element.all(by.className('editreminder'));
        reminderElement.get(0).click();
        element(by.id('editreminderName')).sendKeys('R2');
        element(by.id('editreminderDesc')).sendKeys('Reminder two');
        element(by.id('editreminderType')).sendKeys('Weekly');
        element(by.css('button[type=reminderupdate]')).click();
    });
    // Delete  existing Reminder
    it('Delete  existing Reminder', () => {
        element(by.className('reminderrefresh')).click();
        const reminderElement = element.all(by.css('button[type=reminderdeletebutton]'));
        reminderElement.get(0).click();
    });

});

