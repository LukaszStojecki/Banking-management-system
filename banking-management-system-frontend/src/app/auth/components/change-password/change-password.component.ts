import {Component, OnInit} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {PasswordResetTokenRequest} from "./PasswordResetTokenRequest";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ErrorStateMatcher} from "@angular/material/core";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    return (invalidCtrl || invalidParent);
  }
}
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  changeForm: FormGroup;
  passwordResetTokenRequest: PasswordResetTokenRequest;
  token: string;

  matcher = new MyErrorStateMatcher();


  constructor(private userService: UserService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {
    this.passwordResetTokenRequest = {
      newPassword: '',
      confirmationPassword: ''
    };
    this.activatedRoute.queryParams.subscribe(params => this.token = params[`token`]);
  }

  ngOnInit(): void {
    this.changeForm = this.formBuilder.group({
      newPassword: ['', [Validators.required]],
      confirmationPassword: ['']
    },{validators: this.checkPasswords});
  }


  checkPasswords: ValidatorFn = (group: AbstractControl):  ValidationErrors | null => {
    let pass = group.get('newPassword').value;
    let confirmPass = group.get('confirmationPassword').value
    return pass === confirmPass ? null : { notSame: true }
  }

  changePassword(){
    this.passwordResetTokenRequest.newPassword = this.changeForm.get('newPassword').value;
    this.passwordResetTokenRequest.confirmationPassword = this.changeForm.get('confirmationPassword').value;

    this.userService.changePassword(this.token,this.passwordResetTokenRequest).subscribe(data => {
      console.log("change password success")
      this.router.navigate(['/login'],
        { queryParams: { changed: 'true' } }).then(() =>{
        console.log("successfully navigating to the login view")}).catch((reason => {
        console.log("failed navigating to the login view")
      }));
    }, error => {
      console.log("błąd zmiany hasła")
      this.snackBar.open('Ups, cos poszło nie tak. Spróbuj ponowanie',
        '', {duration: 6000, panelClass: 'red-snackbar',verticalPosition:"top",horizontalPosition:"center"});
    });
  }

}
