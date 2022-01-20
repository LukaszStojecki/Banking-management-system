import {Component, OnInit} from '@angular/core';
import {UserService} from "../../auth/service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn: boolean;
  identificationNumber: string;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.userService.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
    this.userService.identificationNumber.subscribe((data: string) => this.identificationNumber = data);
    this.isLoggedIn = this.userService.isLoggedIn();
    this.identificationNumber = this.userService.getIdentificationNumber();
  }

  logout() {
    this.userService.logout();
    this.isLoggedIn = false;
    this.router.navigateByUrl('');
  }

}
