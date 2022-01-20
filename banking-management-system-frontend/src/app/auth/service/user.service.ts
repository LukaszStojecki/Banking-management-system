import {EventEmitter, Injectable, Output} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LocalStorageService} from "ngx-webstorage";
import {LoginRequest} from "../components/login/login.request";
import {Observable, throwError} from "rxjs";
import {LoginResponse} from "../components/login/login.response";
import {map, tap} from "rxjs/operators";
import {RegisterRequest} from "../components/register/register.request";
import {PasswordResetTokenRequest} from "../components/change-password/PasswordResetTokenRequest";
@Injectable({
  providedIn: 'root'
})
export class UserService {

  angularHost = 'http://localhost:8080/api/auth';

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() identificationNumber: EventEmitter<string> = new EventEmitter();
  @Output() role: EventEmitter<string> = new EventEmitter();
  userRole: string;

  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    identificationNumber: this.getIdentificationNumber()
  }

  constructor(private httpClient: HttpClient,
              private localStorage: LocalStorageService) {
  }


  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getIdentificationNumber() {
    return this.localStorage.retrieve("identificationNumber")
  }

  getRole() {
    return this.userRole;
  }

  refreshToken() {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/refreshToken',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiryDuration');

        this.localStorage.store('authenticationToken',
          response.authenticationToken);
        this.localStorage.store('expiryDuration', response.expiryDuration);
      }));
  }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.httpClient.post<LoginResponse>(this.angularHost + '/login',
      loginRequest).pipe(map(data => {
      this.localStorage.store('authenticationToken', data.authenticationToken);
      this.localStorage.store('identificationNumber', data.identificationNumber);
      this.localStorage.store('refreshToken', data.refreshToken);
      this.localStorage.store('expiryDuration', data.expiryDuration);

      this.loggedIn.emit(true);
      this.identificationNumber.emit(data.identificationNumber);

      return true;
    }));
  }

  forgotPassword(email: string) {
    return this.httpClient.get(this.angularHost + '/resetPassword?email=' + email);
  }

  reminderIdentificationNumber(email: string) {
    return this.httpClient.get(this.angularHost + '/remindIdentificationNumber?email=' + email);
  }

  signup(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post(this.angularHost + '/signup', registerRequest, {responseType: 'text'});
  }

  confirmRegistration(token: string): Observable<any> {
    return this.httpClient.get(this.angularHost + '/verification?token=' + token, {responseType: 'text'})
  }

  changePassword(token: string, passwordResetTokenRequest: PasswordResetTokenRequest): Observable<any> {
    return this.httpClient.put(this.angularHost + '/resetPassword?token=' + token, passwordResetTokenRequest, {responseType: 'text'})
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }


  logout() {
    this.httpClient.post('http://localhost:8080/api/auth/logout', this.refreshTokenPayload,
      {responseType: 'text'})
      .subscribe(data => {
        console.log(data);
      }, error => {
        throwError(error);
      })
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('identificationNumber');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiryDuration');
  }

}
