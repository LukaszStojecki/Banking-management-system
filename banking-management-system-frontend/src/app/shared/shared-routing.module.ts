import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SharedComponent} from "./shared.component";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  {
    path:"",component:SharedComponent,
    children:[
      { path: '**', component: HomeComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SharedRoutingModule { }
