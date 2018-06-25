import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs/Observable";

declare const gapi: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
    simpleObservable = new Observable((observer) => {
        this.checkGapi();
    });

    constructor() {
    }

    checkUserDetails() {
        if (gapi.auth2) {
            var googleAuth = gapi.auth2.getAuthInstance();
            var googleUser = googleAuth.currentUser.get();
            var profile = googleUser.getBasicProfile();

            console.log('Auth is signed in: ' + googleAuth.isSignedIn.get());
            console.log('User is signed in: ' + googleUser.isSignedIn());
            console.log('Hosted Domain: ' + googleUser.getHostedDomain());
            console.log('User has granted: ' + googleUser.getGrantedScopes());
            console.log('User name: ' + profile.getName());
            console.log('User email: ' + profile.getEmail());
            console.log('User image: ' + profile.getImageUrl());

        } else {
            console.log('gapi.auth2 is undefined');
        }
    }

    signOut() {
        if (gapi.auth2) {
            gapi.auth2.getAuthInstance().signOut();
        } else {
            console.log('cant sign out, no gapi');
        }
    }

    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    async checkGapi() {
        await this.sleep(100);

        if (gapi.auth2 != undefined) {
            console.log('gapi.auth2 is initialized');
            return true;
        } else {
            console.log('gapi.auth2 is undefined');
            this.checkGapi();
        }
    }

    ngOnInit() {
        this.simpleObservable.subscribe();
    }

}
