import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  resetGroup: FormGroup

  constructor(private userService: UserService,
              private fb: FormBuilder,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.resetGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() {
    return this.resetGroup.controls;
  }
  resetPassword(){
    this.userService.forgotPassword(this.f.email.value)
      .subscribe(data => {
      }, (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.snackBar.open('Niepoprawny adres e-mail. Wprowadź poprawny adres','',{duration:3000,panelClass: 'red-snackbar',verticalPosition:'top',horizontalPosition:'center'})
        } else {
          console.log(error);
          this.snackBar.open
          ('Sprawdź swoją pocztę,został wysłany e-mail z linkiem do resetowania hasła. ' +
            ' Możesz poprosić o e-mail z nowym linkiem poprzez\n' +
            ' wpisując nowy adres w polu poniżej.','',{duration:6000,panelClass: 'green-snackbar',verticalPosition:'top',horizontalPosition:'center'});
        }
      });
  }
}
