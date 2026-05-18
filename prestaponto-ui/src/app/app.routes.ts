import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

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
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/marketplace/pages/home-page/home-page.component').then(
        (module) => module.HomePageComponent,
      ),
  },
  {
    path: 'categories',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/marketplace/pages/category-page/category-page.component').then(
        (module) => module.CategoryPageComponent,
      ),
  },
  {
    path: 'explore',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/marketplace/pages/explore-page/explore-page.component').then(
        (module) => module.ExplorePageComponent,
      ),
  },
  {
    path: 'catalog-item/:id',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/marketplace/pages/catalog-item-page/catalog-item-page.component').then(
        (module) => module.CatalogItemPageComponent,
      ),
  },
  {
    path: 'disponibilidades/:prestadorId',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/marketplace/pages/disponibilidade-page/disponibilidade-page.component'
      ).then((module) => module.DisponibilidadePageComponent),
  },
  {
    path: 'contracts',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/marketplace/pages/contracts-page/contracts-page.component'
      ).then((module) => module.ContractsPageComponent),
  },
  {
    path: 'provider/catalog',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-catalog-page/provider-catalog-page.component'
      ).then((module) => module.ProviderCatalogPageComponent),
  },
  {
    path: 'provider/catalog/new',
    canActivate: [authGuard],
    loadComponent: () =>
      import(
        './features/marketplace/pages/provider-service-create-page/provider-service-create-page.component'
      ).then((module) => module.ProviderServiceCreatePageComponent),
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];