import {Injectable, NgZone} from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from '../environments/environment';
import {User} from './user';

declare const gapi: any;
@Injectable()
export class AuthenticationService {

    public auth2: any;
    public user$: BehaviorSubject<User> = new BehaviorSubject<User>({
        _id: '',
        SubjectID: '',
        FirstName: '',
        LastName: '',
        ShirtSize: '',
        Role: ''
    });
    public isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    public isLoaded$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


    constructor(private zone: NgZone, private http: HttpClient) { }

    validateToken(token: String): Observable<User> {
        return this.http.get<User>(environment.API_URL + 'login/' + token);
    }

    signIn(): void {
        this.auth2.signIn().then(user => {
            this.validateToken(user.getAuthResponse().id_token).subscribe(user => {
                this.zone.run(() => {
                    this.user$.next(user[0]);
                    this.isLoggedIn$.next(true);
                });
            },
                (err) => {
                    console.log(err);
                });
        });
    }

    signOut(): void {
        this.auth2.signOut().then(() => {
            this.zone.run(() => {
                this.isLoggedIn$.next(false);
                this.user$.next(null);
            });
        },
            (err) => {
                console.log(err);
            });
    }

    loadAuth2(): void {
        gapi.load('auth2', () => {
            gapi.auth2.init({
                client_id: '450453277496-7vivahunsdfj4rvqv4q1pb6e6bqlk4v3.apps.googleusercontent.com',
                fetch_basic_profile: true
            }).then((auth) => {
                    this.zone.run(() => {
                        this.auth2 = auth;
                        this.isLoaded$.next(true);
                    });
                },
            );
        });

        gapi.signin2.render('my-signin2', {
            'scope': 'profile email',
            'width': 240,
            'height': 50,
            'longtitle': true,
            'theme': 'light',
            'onsuccess': param => {
                this.getUser(param.getBasicProfile().getId()).subscribe(user => {
                    this.user$ = user[0];
                    this.isLoggedIn$.next(true);
                })
            },
            'onfailure': param => {
                console.log('fail');
            }
        });

    }

    getUser(subjectID: string): Observable<User> {
        return this.http.get<User>(environment.API_URL + 'userr/' + subjectID);
    }

}
