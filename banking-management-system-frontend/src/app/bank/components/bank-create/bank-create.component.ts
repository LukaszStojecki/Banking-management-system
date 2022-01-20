import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BankService} from "../../service/bank.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BankAccount} from "./BankAccount";
import {BankAccountType} from "./BankAccountType";
import {Saldo} from "./Saldo";

@Component({
  selector: 'app-bank-create',
  templateUrl: './bank-create.component.html',
  styleUrls: ['./bank-create.component.css']
})
export class BankCreateComponent implements OnInit {

  bankAccountTypes: Array<BankAccountType>;
  bankAccountForm: FormGroup;
  myBankAccounts: Array<BankAccount>;

  switchIndex: number = -1;

  constructor(private bankService: BankService,
              private snackBar: MatSnackBar,
              private fb: FormBuilder) {
    this.bankAccountForm = fb.group({ bankAccountType: ['', Validators.required] });
  }

  ngOnInit(): void {
    this.fetchBankAccounts()
    this.fetchBankAccountTypes();
  }

  changeBankAccountFormType(index: number) {
    if (index >= 0 && index < this.bankAccountTypes.length) {
      this.switchIndex = index;
      this.bankAccountForm.get('bankAccountType').setValue(this.bankAccountTypes[index]);
    }
  }

  createBank() {
    this.bankService.createBankAccount(this.bankAccountForm.value)
      .subscribe(res => {
        this.snackBar.open('Utworzono nowy rachunek',
          '', {duration: 6000, panelClass: 'green-snackbar', verticalPosition: "top", horizontalPosition: "center"});
      });
  }

  fetchBankAccounts() {
    this.bankService.findByUser()
      .subscribe(res =>
        this.myBankAccounts = res);
  }

  fetchBankAccountTypes() {
    this.bankService.findByBankAccountType()
      .subscribe(res => this.bankAccountTypes = res);
  }

}
