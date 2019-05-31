import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { log } from 'util';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  submitMessage: String;
  private bearerToken: any;
  constructor(private authService: AuthenticationService, private routerService: RouterService) { }
  username = new FormControl('', Validators.compose([Validators.required, Validators.minLength(5)]));
  password = new FormControl('', Validators.compose([Validators.required, Validators.minLength(5)]));

  loginSubmit() {
    this.authService.authenticateUser(this.username.value, this.password.value).subscribe(res => {

      // To authenticate user based on bearerToken
      this.authService.setBearerToken(res['token']);
      this.authService.setUserId(res['UserId']);
      this.routerService.routeToDashboard();
    },
      err => { // When invalid user credentails enters then it will show below proper error message
        this.submitMessage = 'Unauthorized / Entered as invalid user details';
      }
    );
  }

  userSignup() {
    this.routerService.routeToSignUp();
  }

}



