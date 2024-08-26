import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { ProductFormComponent } from './pages/product/product-form/product-form.component';
import { ProductListComponent } from './pages/product/product-list/product-list.component';
import { ProductManagementComponent } from './pages/product/product-management/product-management.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent},
    { path: 'products', component: ProductManagementComponent },
    { path: 'products/add', component: ProductFormComponent },
    { path: 'products/edit/:id', component: ProductFormComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];
