import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BankCreateComponent} from "./components/bank-create/bank-create.component";

const routes: Routes = [
  {path: 'bank-create', component: BankCreateComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BankRoutingModule {
}
