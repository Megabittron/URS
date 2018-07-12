import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../authentication.service";
import {User} from "../user";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {AccountDialogComponent} from "../account-dialog/account-dialog.component";

@Component({
    selector: 'app-nav',
    templateUrl: 'nav.component.html',
    styleUrls: ['nav.component.scss'],
})
export class NavComponent implements OnInit{
    public text: string;

    public authIsLoaded: boolean = false;
    public isLoggedIn: boolean = false;
    public user: User;
    public profilePic: string = null;
    public infoState: string = 'inactive';

    constructor(private authenticationService: AuthenticationService,
                private dialog: MatDialog) {
        this.text = 'Nav';
    }

    openDialog() {

        const dialogConfig = new MatDialogConfig();

        dialogConfig.position = {
            'top': '0',
            'right': '50'
        };

        this.dialog.open(AccountDialogComponent, dialogConfig);
    }

    signOut(): void {
        this.authenticationService.signOut();
    }

    ngOnInit() {
        this.authenticationService.isLoaded$.subscribe( value => {
            this.authIsLoaded = value;
        });

        this.authenticationService.isLoggedIn$.subscribe( value => {
            this.isLoggedIn = value;
        });

        this.authenticationService.user$.subscribe(value => {
            this.user = value;
            this.profilePic = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getImageUrl();
        })

    }

}
