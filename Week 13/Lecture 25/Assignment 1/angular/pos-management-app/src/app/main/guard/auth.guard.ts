import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../../services/auth.service';
import { RouterConfig } from '../../config/app.constants';

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  if (loginService.getToken()) {
    return true;
  } else {
    router.navigate([RouterConfig.LOGIN.link]);
    return false;
  }
};
