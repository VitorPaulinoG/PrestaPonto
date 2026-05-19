import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

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
    path: 'home',
    canActivate: [roleGuard],
    data: { roles: ['CLIENT'] },
    loadComponent: () =>
      import('./features/marketplace/pages/home-page/home-page.component').then(
        (module) => module.HomePageComponent,
      ),
  },
  {
    path: 'categories',
    canActivate: [roleGuard],
    data: { roles: ['CLIENT'] },
    loadComponent: () =>
      import('./features/marketplace/pages/category-page/category-page.component').then(
        (module) => module.CategoryPageComponent,
      ),
  },
  {
    path: 'explore',
    canActivate: [roleGuard],
    data: { roles: ['CLIENT'] },
    loadComponent: () =>
      import('./features/marketplace/pages/explore-page/explore-page.component').then(
        (module) => module.ExplorePageComponent,
      ),
  },
  {
    path: 'catalog-item/:id',
    canActivate: [roleGuard],
    data: { roles: ['CLIENT'] },
    loadComponent: () =>
      import('./features/marketplace/pages/catalog-item-page/catalog-item-page.component').then(
        (module) => module.CatalogItemPageComponent,
      ),
  },
  {
    path: 'disponibilidades/:prestadorId',
    canActivate: [roleGuard],
    data: { roles: ['CLIENT'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/disponibilidade-page/disponibilidade-page.component'
      ).then((module) => module.DisponibilidadePageComponent),
  },
  {
    path: 'contracts',
    canActivate: [roleGuard],
    data: { roles: ['CLIENT'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/contracts-page/contracts-page.component'
      ).then((module) => module.ContractsPageComponent),
  },
  {
    path: 'profile',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/marketplace/pages/profile-page/profile-page.component'
      ).then((module) => module.ProfilePageComponent),
  },
  {
    path: 'provider/catalog',
    canActivate: [roleGuard],
    data: { roles: ['PROVIDER'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-catalog-page/provider-catalog-page.component'
      ).then((module) => module.ProviderCatalogPageComponent),
  },
  {
    path: 'provider/catalog/new',
    canActivate: [roleGuard],
    data: { roles: ['PROVIDER'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-service-create-page/provider-service-create-page.component'
      ).then((module) => module.ProviderServiceCreatePageComponent),
  },
  {
    path: 'provider/contracts',
    canActivate: [roleGuard],
    data: { roles: ['PROVIDER'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-contracts-page/provider-contracts-page.component'
      ).then((module) => module.ProviderContractsPageComponent),
  },
  {
    path: 'provider/agenda',
    canActivate: [roleGuard],
    data: { roles: ['PROVIDER'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-agenda-page/provider-agenda-page.component'
      ).then((module) => module.ProviderAgendaPageComponent),
  },
  {
    path: 'provider/agenda/new',
    canActivate: [roleGuard],
    data: { roles: ['PROVIDER'] },
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-agenda-create-page/provider-agenda-create-page.component'
      ).then((module) => module.ProviderAgendaCreatePageComponent),
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
