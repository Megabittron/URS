import {Component} from '@angular/core';

@Component({
    selector: 'app-nav',
    templateUrl: 'nav.component.html',
    styleUrls: [],
    providers: []
})
export class NavComponent {
    public text: string;

    constructor() {
        this.text = 'Nav';
    }
}
