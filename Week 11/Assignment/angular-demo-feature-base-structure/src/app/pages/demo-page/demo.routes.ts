import { Routes } from '@angular/router';
import { DemoComponent } from './demo/demo.component';
import { NameEditorComponent } from './name-editor/name-editor.component';

export const demoRoutes: Routes = [
  { path: 'common', component: DemoComponent },
  { path: 'form', component: NameEditorComponent }
];
