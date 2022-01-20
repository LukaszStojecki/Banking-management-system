import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BankAccount} from "../components/bank-create/BankAccount";
import {BankAccountType} from "../components/bank-create/BankAccountType";

@Injectable({
  providedIn: 'root'
})
export class BankService {

  angularHost = 'http://localhost:8080/api/bank/account';

  constructor(
    private httpClient: HttpClient) {
  }

  createBankAccount(bankAccount: BankAccount): Observable<BankAccount> {
    return this.httpClient.post<BankAccount>(this.angularHost + '/create', bankAccount);
  }

  findByUser(): Observable<Array<BankAccount>> {
    return this.httpClient.get<Array<BankAccount>>(this.angularHost + '/byUser');
  }

  findByBankAccountType(){
    return this.httpClient.get<Array<BankAccountType>>(this.angularHost + '/byType');
  }
}
