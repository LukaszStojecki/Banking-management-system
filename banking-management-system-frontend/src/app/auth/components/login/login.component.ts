import {Component, Directive, EventEmitter, HostListener, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginRequest} from "./login.request";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequest: LoginRequest;
  isError: boolean;
  capsOn;
  isRequestSend = false;

  constructor(private userService: UserService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {
    this.loginRequest = {
      identificationNumber: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      identificationNumber: ['', [ Validators.required]],
      password: ['', [ Validators.required]]
    });
  }

  login() {
    this.isRequestSend = true;
    this.loginRequest.identificationNumber = this.loginForm.get('identificationNumber').value;
    this.loginRequest.password = this.loginForm.get('password').value;

    this.userService.login(this.loginRequest).subscribe(data => {
      this.isRequestSend = false;
      this.isError = false;
      console.log("login success")
      this.router.navigateByUrl('');
      this.snackBar.open('Zalogowano. Za chwilę nastąpi przekierowanie',
        '',{duration:6000, panelClass:'green-snackbar',verticalPosition:"top",horizontalPosition:"center"})
    }, error => {
      this.isError = true;
      this.isRequestSend = false;
      console.log("błąd logowania")
      this.snackBar.open('Niepoprawny identyfikator lub hasło',
        '', {duration: 6000, panelClass: 'red-snackbar',verticalPosition:"top",horizontalPosition:"center"});
    });
  }


}

@Directive({ selector: '[capsLock]' })
export class TrackCapsDirective {
  @Output('capsLock') capsLock = new EventEmitter<Boolean>();

  @HostListener('window:keydown', ['$event'])
  onKeyDown(event: KeyboardEvent): void {
    this.capsLock.emit(event.getModifierState && event.getModifierState('CapsLock'));
  }
  @HostListener('window:keyup', ['$event'])
  onKeyUp(event: KeyboardEvent): void {
    this.capsLock.emit(event.getModifierState && event.getModifierState('CapsLock'));
  }

}
