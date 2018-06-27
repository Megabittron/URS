import {Injectable, NgZone} from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {HttpClientModule} from "@angular/common/http";
/*import {User} from './user';*/

declare const gapi: any;
@Injectable()
export class AuthenticationService {
    public auth2: any;
    public user$: BehaviorSubject<gapi.auth2.GoogleUser> = new BehaviorSubject<gapi.auth2.GoogleUser>(null);
    public isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    public isLoaded$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


    constructor(private zone: NgZone, private http: HttpClientModule) { }



}
