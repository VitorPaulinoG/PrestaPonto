import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login-page/login-page.component').then(
        (module) => module.LoginPageComponent,
      ),
  },
  {
    path: 'signup',
    loadComponent: () =>
      import('./features/auth/pages/signup-page/signup-page.component').then(
        (module) => module.SignupPageComponent,
      ),
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
