import { Routes } from '@angular/router';
import { RouterConfig } from './config/app.constants';

export const routes: Routes = [
  {
    path: RouterConfig.CUSTOMER.path,
    loadChildren: () =>
        import('./pages/customer/customer.routes')
            .then(m => m.customerRoutes)
  },
  {
    path: RouterConfig.DEMO.path,
    loadChildren: () =>
        import('./pages/demo-page/demo.routes')
            .then(m => m.demoRoutes)
  }
];
