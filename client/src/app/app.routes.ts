// Imports
import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AdminComponent} from './admin/admin.component';
import {AccountInfoComponent} from './accountInfo/accountInfo.component';

// Route Configuration
export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'admin', component: AdminComponent},
    {path: 'accountInfo', component: AccountInfoComponent}
];

export const Routing: ModuleWithProviders = RouterModule.forRoot(routes);
