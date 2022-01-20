import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {BankRoutingModule} from './bank-routing.module';
import {BankCreateComponent} from './components/bank-create/bank-create.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatTableModule} from "@angular/material/table";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatButtonModule} from "@angular/material/button";


@NgModule({
  declarations: [BankCreateComponent],
  imports: [
    CommonModule,
    BankRoutingModule,
    MatToolbarModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatTooltipModule,
    MatButtonModule
  ],
  exports: [
    BankCreateComponent
  ]
})
export class BankModule {
}
