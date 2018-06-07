import {Component} from '@angular/core';

@Component({
    templateUrl: 'accountInfo.component.html'
})
export class AccountInfoComponent {
    public text: string;

    constructor() {
        this.text = 'Account Info';
    }
}
