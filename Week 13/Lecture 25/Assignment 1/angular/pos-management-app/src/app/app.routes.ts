import { Routes } from '@angular/router';
import { RouterConfig } from './config/app.constants';
import { authGuard } from './main/guard/auth.guard';

export const routes: Routes = [
  {
    path: RouterConfig.LOGIN.path,
    loadChildren: () =>
        import('./pages/login/login.routes')
            .then(m => m.loginRoutes)
  },
  {
    path: '',
    canActivate: [authGuard],
    children: [
      {
        path: RouterConfig.PRODUCT.path,
        loadChildren: () =>
            import('./pages/product/product.routes')
                .then(m => m.productRoutes)
      },
      {
        path: RouterConfig.INVOICE.path,
        loadChildren: () =>
            import('./pages/invoice/invoice.routes')
                .then(m => m.invoiceRoutes)
      },
      {
        path: RouterConfig.CUSTOMER.path,
        loadChildren: () =>
            import('./pages/customer/customer.routes')
                .then(m => m.customerRoutes)
      }
    ]
  }
];
