import {Component, OnInit} from '@angular/core';
import { Observable } from "rxjs/Observable";
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";

declare const gapi: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

    public authIsLoaded: boolean = false;
    public isLoggedIn: boolean = false;
    public user: User;

    constructor(private authenticationService: AuthenticationService) { }

    checkUserDetails() {
        console.log('User: ' + this.user.FirstName);
        console.log('Authloaded: ' + this.authIsLoaded);
        console.log('IsLoggedIn: ' + this.isLoggedIn);
        console.log(gapi.auth2.getAuthInstance().currentUser.get().isSignedIn());
        console.log(this.authenticationService.isLoggedIn$.getValue());

    }

    signIn(): void {
        this.authenticationService.signIn();
    };

    signOut(): void {
        this.authenticationService.signOut();
    }

    ngOnInit() {
        this.authenticationService.isLoaded$.subscribe( value => {
            console.log(value);
            this.authIsLoaded = value;
        });

        this.authenticationService.isLoggedIn$.subscribe( value => {
            console.log(value);
            this.isLoggedIn = value;
        });

        this.authenticationService.user$.subscribe( value => {
            this.user = value;
            console.log(value);
        });

        this.authenticationService.loadAuth2();
    }
}
