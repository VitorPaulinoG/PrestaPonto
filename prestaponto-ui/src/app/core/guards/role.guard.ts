import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { TokenService } from '../services/token.service';

export const roleGuard: CanActivateFn = (route) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  if (!tokenService.hasToken()) {
    return router.createUrlTree(['/login']);
  }

  const allowedRoles: string[] | undefined = route.data?.['roles'];

  if (!allowedRoles || allowedRoles.length === 0) {
    return true;
  }

  const userRole = tokenService.getRole();

  if (userRole && allowedRoles.includes(userRole)) {
    return true;
  }

  return router.createUrlTree(['/login']);
};
