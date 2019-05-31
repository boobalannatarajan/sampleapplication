import { Injectable } from '@angular/core';
import { User } from '../signup/user';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './authentication.service';
import { tap } from 'rxjs/operators';
import 'rxjs/add/operator/do';

@Injectable()
export class UserService {

  constructor(private httpClient: HttpClient, private authService: AuthenticationService) { }
  // For user registration and it will be save to both Mondo DB and MYSQL DB
  registerUserService(user: User) {
    return this.httpClient.post<User>('http://localhost:8084/v1/user',
      { userId: user.userId, userName: user.firstName, userPassword: user.userPassword, userMobile: user.userMobile }, {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      });

  }
  // For user authentication post registration
  registerUserAuthService(user: User) {
    return this.httpClient.post<User>('http://localhost:8083/api/v1/auth/register',
      {
        userId: user.userId, userPassword: user.userPassword,
        firstName: user.firstName, lastName: user.lastName, userRole: user.userRole
      }, {
        headers: new HttpHeaders().set('authorization', `Bearer ${this.authService.getBearerToken()}`)
      });

  }

}
