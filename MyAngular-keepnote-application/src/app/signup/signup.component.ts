import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from './user';
import { RouterService } from '../services/router.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  private rolesList: string[];
  signUpForm: NgForm;
  user: User;
  submitMessage: String;
  userId: String;
  userName: String;
  constructor(private router: RouterService, private userService: UserService) {
    this.rolesList = ['Admin', 'Developer', 'Guest', 'User'];
    this.user = new User();
  }

  ngOnInit() { }

  signUpUser(signUpForm: NgForm) {
    this.userService.registerUserService(signUpForm.value).subscribe(res => {
      this.submitMessage = 'Welcome User --> ' + res['userName'] + ' Created Successfully.Proceed To Login';
    }
      , err => {
        if (err.message === 'Http failure response for http://localhost:8084/v1/user: 409 OK') {
          this.submitMessage = 'User Already Exists';
          alert('User Already Exists'); // user exist then
        }
      });

    this.userService.registerUserAuthService(signUpForm.value).subscribe(res => {
      // Alert message for successful authentication
      alert(' Hi ' + res['userId'] + ' Click ok to Proceed Login');
    }
      , err => {

      });

    signUpForm.reset(); // For reset form

  }
}
