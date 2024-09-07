import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RouterConfig } from '../../config/app.constants';

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(AuthService);
  const router = inject(Router);

  if (loginService.getToken()) {
    return true;
  } else {
    router.navigate([RouterConfig.LOGIN.link]);
    return false;
  }
};
