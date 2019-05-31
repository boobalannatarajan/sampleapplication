import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

  private authUrl = 'http://localhost:8083/api/v1/auth/login/';

  constructor( public http: HttpClient ) {}
  // For user Authentication with setting content type
  authenticateUser(userId: string, userPassword: string) {
    return this.http.post<any>(this.authUrl, {userId: userId, userPassword: userPassword}, {
      headers: new HttpHeaders().set('Content-Type', `application/json`)
    });
  }
  // To set bearer token for user authentication
  setBearerToken(token) {
    localStorage.setItem('bearerToken', token);
  }
  // Get bearer token from window localstorage
  getBearerToken() {
    return localStorage.getItem('bearerToken');
  }

  setUserId(userId) {
    localStorage.setItem('UserId', userId);
  }

  getUserId() {
    return localStorage.getItem('UserId');
  }
  // Validate the user authentication based on setter bearer token value
  public isUserAuthenticated(token): Promise<boolean> {
    return this.http.post(`${this.authUrl}isAuthenticated`, {}, {
      headers: new HttpHeaders().set('authorization', `Bearer ${token}`)
    }).map((res) => res['isAuthenticated'] ).toPromise();
  }
  // To check user is valid or not along with token value
  public isUserValid(token) {
    return this.http.post(`${this.authUrl}isAuthenticated`, {}, {
      headers: new HttpHeaders().set('authorization', `Bearer ${token}`)
    });
  }
}
