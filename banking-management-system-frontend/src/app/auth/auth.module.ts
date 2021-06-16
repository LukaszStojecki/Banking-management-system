import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuthRoutingModule} from './auth-routing.module';
import {LoginComponent, TrackCapsDirective} from './components/login/login.component';
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


@NgModule({
  declarations: [LoginComponent, TrackCapsDirective],
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
    MatIconModule
  ],
  exports: [
    LoginComponent
  ]

})
export class AuthModule {
}
