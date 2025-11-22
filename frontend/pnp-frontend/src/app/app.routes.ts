import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './modules/auth/login/login.component';
import { PersonalListComponent } from './modules/personal/personal-list/personal-list.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    {
        path: 'dashboard',
        component: PersonalListComponent,
        canActivate: [authGuard]
    },
    {
        path: 'personal',
        component: PersonalListComponent,
        canActivate: [authGuard]
    },
    { path: '**', redirectTo: '/login' }
];
