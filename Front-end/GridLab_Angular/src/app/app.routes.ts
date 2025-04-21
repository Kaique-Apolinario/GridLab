import { Routes } from '@angular/router';
import { UploadButtonComponent } from './components/upload-button/upload-button.component';
import { ContainerComponent } from './components/container/container.component';

export const routes: Routes = [
    {path: '', pathMatch: "full", component: ContainerComponent}
];
