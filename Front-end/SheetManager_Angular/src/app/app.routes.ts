import { Routes } from '@angular/router';
import { UploadButtonComponent } from './components/upload-button/upload-button.component';

export const routes: Routes = [
    {path: '', pathMatch: "full", component: UploadButtonComponent}
];
