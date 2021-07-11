import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {ResetPasswordComponent} from "./components/reset-password/reset-password.component";
import {ReminderComponent} from "./components/reminder/reminder.component";
import {RegisterComponent} from "./components/register/register.component";
import {RegistrationVerifyComponent} from "./components/registration-verify/registration-verify.component";
import {ChangePasswordComponent} from "./components/change-password/change-password.component";

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'reset-password',component:ResetPasswordComponent},
  {path:'reminder-number',component:ReminderComponent},
  {path:'register',component:RegisterComponent},
  {path:'registration-verify',component:RegistrationVerifyComponent},
  {path:'change-password',component:ChangePasswordComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
