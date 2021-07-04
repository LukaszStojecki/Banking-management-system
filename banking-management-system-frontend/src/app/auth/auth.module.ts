import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuthRoutingModule} from './auth-routing.module';
import {LoginComponent, TrackCapsDirective} from './components/login/login.component';
import {ResetPasswordComponent} from './components/reset-password/reset-password.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FlexModule} from "@angular/flex-layout";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {NgxWebstorageModule} from "ngx-webstorage";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {ReminderComponent} from "./components/reminder/reminder.component";
import {RegisterComponent} from "./components/register/register.component";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {NgxMaskModule} from "ngx-mask";
import { RegistrationVerifyComponent } from './components/registration-verify/registration-verify.component';


@NgModule({
  declarations: [LoginComponent, TrackCapsDirective, ResetPasswordComponent, ReminderComponent, RegisterComponent, RegistrationVerifyComponent],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    FlexModule,
    MatTooltipModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    NgxWebstorageModule.forRoot(),
    HttpClientModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    MatMenuModule,
    MatIconModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    NgxMaskModule.forRoot()
  ],
  exports: [
    ResetPasswordComponent,
    ReminderComponent,
    RegisterComponent,
    LoginComponent,
    RegistrationVerifyComponent
  ]

})
export class AuthModule {
}
