import { LoginPage } from './page-objects/login.po';
import { browser, by, element, ElementFinder, promise, protractor } from 'protractor';

describe('New User Registration Functionality :', () => {
    let page: LoginPage;

    beforeEach(() => {
        page = new LoginPage();
    });
    //  Sign Up button Functionality
    it('Sign Up Functionality test ', () => {
        page.navigateToSignup();
        element(by.id('inputUserId')).sendKeys('testFSEuser');
        element(by.id('inputPassword')).sendKeys('testFSEuser');
        element(by.id('inputFirstname')).sendKeys('boobala');
        element(by.id('inputLastname')).sendKeys('natarajan');
        element(by.id('inputuserMobile')).sendKeys('9942153363');
        element(by.css('input[type=checkbox]')).click();
        element(by.cssContainingText('option', 'Developer')).click();
        element(by.css('button[type=submit]')).click();
    });

});

