import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';

import {environment} from '../../environments/environment';


@Injectable()
export class UserListService {
    readonly baseUrl: string = environment.API_URL + 'users';
    private userUrl: string = this.baseUrl;

    constructor(private http: HttpClient) {
    }
}