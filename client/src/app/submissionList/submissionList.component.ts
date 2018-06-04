import {Component} from '@angular/core';

@Component({
    templateUrl: 'submissionList.component.html'
})
export class SubmissionListComponent {
    public text: string;

    constructor() {
        this.text = 'Submission List';
    }
}
