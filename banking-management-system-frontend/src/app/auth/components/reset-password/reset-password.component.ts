import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  formGroup: FormGroup
  isError: boolean;

  constructor(private userService: UserService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      email: ['', [ Validators.required]]
    });
  }
    get f() {
    return this.formGroup.controls;
  }

  resetPassword() {
      this.isError = false;
      this.userService.forgotPassword(this.f.email.value)
        .subscribe(res => {
            console.log("check email")
        }, error => {
          console.log(error)
          this.isError = true;
        })
  }
}
