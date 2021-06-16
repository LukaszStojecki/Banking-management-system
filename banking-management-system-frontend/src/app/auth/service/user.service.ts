import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LocalStorageService} from "ngx-webstorage";
import {LoginRequest} from "../components/login/login.request";
import {Observable} from "rxjs";
import {LoginResponse} from "../components/login/login.response";
import {map, tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  angularHost = 'http://localhost:8080/api/auth';
  resetPasswordUrl = "http://localhost:4200/reset-password/";
  reminderUrl = "http://localhost:4200/reminder-number/";

  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    identificationNumber: this.getIdentificationNumber()
  }

  constructor(private httpClient: HttpClient,
              private localStorage: LocalStorageService) { }


  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }
  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getIdentificationNumber(){
    return this.localStorage.retrieve("identificationNumber")
  }

  refreshToken() {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/refreshToken',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');

        this.localStorage.store('authenticationToken',
          response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.httpClient.post<LoginResponse>(this.angularHost + '/login',
      loginRequest).pipe(map(data => {
      this.localStorage.store('authenticationToken', data.authenticationToken);
      this.localStorage.store('identificationNumber', data.identificationNumber);
      this.localStorage.store('refreshToken', data.refreshToken);
      this.localStorage.store('expiresAt', data.expiresAt);

      return true;
    }));
  }

  forgotPassword(email: string){
    let headers = new HttpHeaders({
      resetPasswordUrl: this.resetPasswordUrl
    });
    let options = {headers: headers}
    return this.httpClient.get(this.angularHost + '/resetPassword?email=' + email,options);
  }

  reminderIdentificationNumber(email: string){
    let headers = new HttpHeaders({
      reminderUrl: this.reminderUrl
    });
    let options = {headers: headers}
    return this.httpClient.get(this.angularHost + '/remindIdentificationNumber?email=' + email,options);
  }
}
