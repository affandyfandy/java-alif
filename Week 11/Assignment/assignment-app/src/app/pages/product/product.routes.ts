import { Routes } from '@angular/router';
import { ProductManagementComponent } from './product-management/product-management.component';
import { ProductFormComponent } from './product-form/product-form.component';

export const productRoutes: Routes = [
  { path: '', component: ProductManagementComponent },
  { path: 'add', component: ProductFormComponent},
  { path: 'edit/:id', component: ProductFormComponent },
];
