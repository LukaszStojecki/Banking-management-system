import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {ResetPasswordComponent} from "./components/reset-password/reset-password.component";
import {ReminderComponent} from "./components/reminder/reminder.component";

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'reset-password',component:ResetPasswordComponent},
  {path:'reminder-number',component:ReminderComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
