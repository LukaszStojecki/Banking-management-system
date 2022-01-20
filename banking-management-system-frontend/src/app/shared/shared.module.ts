import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedRoutingModule} from './shared-routing.module';
import {SharedComponent} from "./shared.component";
import {MatTooltipModule} from "@angular/material/tooltip";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthModule} from "../auth/auth.module";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatMenuModule} from "@angular/material/menu";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {NgxWebstorageModule} from "ngx-webstorage";
import {NgxMaskModule} from "ngx-mask";
import {BankModule} from "../bank/bank.module";
import {MatTableModule} from "@angular/material/table";


@NgModule({
  declarations: [
    SharedComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
    MatTooltipModule,
    FlexLayoutModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    FormsModule,
    ReactiveFormsModule,
    AuthModule,
    MatSnackBarModule,
    MatMenuModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgxWebstorageModule.forRoot(),
    NgxMaskModule.forRoot(),
    BankModule
  ]
})
export class SharedModule {
}
