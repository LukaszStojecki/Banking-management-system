import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-reminder',
  templateUrl: './reminder.component.html',
  styleUrls: ['./reminder.component.css']
})
export class ReminderComponent implements OnInit {

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

  reminderIdentificationNumber() {
    this.isError = false;
    this.userService.reminderIdentificationNumber(this.f.email.value)
      .subscribe(res => {
        console.log("check email")
      }, error => {
        console.log(error)
        this.isError = true;
      })
  }

}
