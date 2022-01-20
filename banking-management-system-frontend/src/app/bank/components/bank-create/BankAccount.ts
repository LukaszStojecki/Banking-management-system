import {BankAccountType} from "./BankAccountType";
import {Saldo} from "./Saldo";

export class BankAccount {
  number: string;
  bankAccountType: BankAccountType;
  saldo: Array<Saldo>;
}
