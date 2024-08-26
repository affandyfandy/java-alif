import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { RouterConfig } from './config/app.constants';

export const routes: Routes = [
    {
        path: RouterConfig.AUTH.path,
        loadChildren: () =>
            import('./pages/auth/auth.routes')
                .then(m => m.authRoutes)
    },
    {
        path: RouterConfig.PRODUCT.path,
        loadChildren: () =>
            import('./pages/product/product.routes')
                .then(m => m.productRoutes)
    },
    {
        path: RouterConfig.HOME.path,
        component: HomeComponent
    }
];
