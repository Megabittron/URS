import {Component} from '@angular/core';

@Component({
    templateUrl: 'admin.component.html'
})
export class AdminComponent {
    public text: string;

    constructor() {
        this.text = 'Admin';
    }
}
