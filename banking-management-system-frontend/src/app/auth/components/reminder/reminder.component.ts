import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-reminder',
  templateUrl: './reminder.component.html',
  styleUrls: ['./reminder.component.css']
})
export class ReminderComponent implements OnInit {

  formGroup: FormGroup

  constructor(private userService: UserService,
              private fb: FormBuilder,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  reminderIdentificationNumber() {
    this.userService.reminderIdentificationNumber(this.f.email.value)
      .subscribe(data => {
      }, (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.snackBar.open('Niepoprawny adres e-mail. Wprowadź poprawny adres', '', {
            duration: 3000,
            panelClass: 'red-snackbar',
            verticalPosition: 'top',
            horizontalPosition: 'center'
          })
        } else {
          console.log(error);
          this.snackBar.open
          ('Sprawdź swoją pocztę,został wysłany e-mail z Twoim loginem. ' +
            ' Możesz poprosić o e-mail z nowym linkiem poprzez\n' +
            ' wpisując nowy adres w polu poniżej.', '', {
            duration: 6000,
            panelClass: 'green-snackbar',
            verticalPosition: 'top',
            horizontalPosition: 'center'
          });
        }
      });
  }

}
