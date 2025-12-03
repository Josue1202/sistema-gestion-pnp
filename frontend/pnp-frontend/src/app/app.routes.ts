import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './modules/auth/login/login.component';
import { MainLayoutComponent } from './shared/layout/main-layout.component';
import { PersonalListComponent } from './modules/personal/personal-list/personal-list.component';
import { PerfilComponent } from './modules/personal/perfil/perfil.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { UnidadListComponent } from './modules/unidades/unidad-list.component';
import { GradoListComponent } from './modules/grados/grado-list.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    {
        path: '',
        component: MainLayoutComponent,
        canActivate: [authGuard],
        children: [
            { path: '', redirectTo: 'personal', pathMatch: 'full' },
            { path: 'personal', component: PersonalListComponent },
            { path: 'personal/:id', component: PerfilComponent },
            { path: 'dashboard', component: DashboardComponent },
            { path: 'unidades', component: UnidadListComponent },
            { path: 'grados', component: GradoListComponent }
        ]
    },
    { path: '**', redirectTo: 'login' }
];
