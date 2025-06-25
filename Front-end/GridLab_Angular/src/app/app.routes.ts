import { Routes } from '@angular/router';
import { ContainerComponent } from './components/container/container.component';
import { FileLibComponent } from './components/file-lib/file-lib.component';
import { LoginInSignUpComponent } from './components/login-in-sign-up/login-in-sign-up.component';
import { authGuard } from './guard/auth.guard';

export const routes: Routes = [
    {path: '', pathMatch: "full", component: ContainerComponent},
    {path: 'login', pathMatch: "full", component: LoginInSignUpComponent},
    {path: 'fileLib', 
        pathMatch: 'full',
        component: FileLibComponent,
        canActivate: [authGuard]},
    {path: 'fileLib/:id', 
        pathMatch: 'full',
        component: FileLibComponent,
        canActivate: [authGuard]},
        {path: 'logout', 
        pathMatch: 'full',
        component: FileLibComponent,
        canActivate: [authGuard]}
];