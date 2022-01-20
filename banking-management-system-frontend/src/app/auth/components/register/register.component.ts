import {Component, OnInit} from '@angular/core';
import {RegisterRequest} from "./register.request";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerRequest: RegisterRequest;
  registerForm: FormGroup
  isRequestSent: boolean;
  isError: boolean;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private router: Router,
              private toastr: ToastrService,
              private snackBar: MatSnackBar) {
    this.registerRequest = {
      firstName: '',
      lastName: '',
      password: '',
      email: '',
      city: '',
      street: '',
      postCode: '',
      houseNumber: '',
      phoneNumber: '',
      dateOfBirth: ''
    };
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      city: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      dateOfBirth: ['', Validators.required],
      password: ['', Validators.required],
      houseNumber: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required]],
      postCode: ['', [Validators.required]],
      street: ['', [Validators.required, Validators.minLength]],
      lastName: ['', [Validators.required]],
    });
  }

  signup() {
    this.isRequestSent = true;
    this.registerRequest.firstName = this.registerForm.get('firstName').value;
    this.registerRequest.lastName = this.registerForm.get('lastName').value;
    this.registerRequest.password = this.registerForm.get('password').value;
    this.registerRequest.email = this.registerForm.get('email').value;
    this.registerRequest.city = this.registerForm.get('city').value;
    this.registerRequest.street = this.registerForm.get('street').value;
    this.registerRequest.postCode = this.registerForm.get('postCode').value;
    this.registerRequest.houseNumber = this.registerForm.get('houseNumber').value;
    this.registerRequest.phoneNumber = this.registerForm.get('phoneNumber').value;
    this.registerRequest.dateOfBirth = this.registerForm.get('dateOfBirth').value;

    this.userService.signup(this.registerRequest).subscribe(data => {
      this.isRequestSent = false;
      this.isError = false;
      console.log("register success")
      this.router.navigate(['/login'],
        {queryParams: {registered: 'true'}}).then(() => {
        console.log("successfully navigating to the login view")
      }).catch((reason => {
        console.log("failed navigating to the login view")
      }));
    }, error => {
      this.isError = true;
      this.isRequestSent = false;
      console.log("błąd rejestracji")
      this.snackBar.open('Ups, cos poszło nie tak.',
        '', {duration: 6000, panelClass: 'red-snackbar', verticalPosition: "top", horizontalPosition: "center"});
    });
  }
}
