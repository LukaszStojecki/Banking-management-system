import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-registration-verify',
  templateUrl: './registration-verify.component.html',
  styleUrls: ['./registration-verify.component.css']
})
export class RegistrationVerifyComponent implements OnInit {

  subscription: Subscription = new Subscription();
  token:string;

  constructor(private userService: UserService,
              private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => this.token = params[`token`]);
  }

  ngOnInit(): void {
    this.subscription.add(
      this.userService.confirmRegistration(this.token).subscribe()
    );
  }
}
