import {Component} from '@angular/core';
import {AppService} from "../app.service";
import {Router} from "@angular/router";
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'app-accountInfo-component',
    templateUrl: 'accountInfo.component.html',
    styleUrls: ['./accountInfo.component.css'],
    providers: [AppService]
})
export class AccountInfoComponent {
    public text: string;

    constructor(private router: Router) {
        this.text = 'Account Info';
    }
}
