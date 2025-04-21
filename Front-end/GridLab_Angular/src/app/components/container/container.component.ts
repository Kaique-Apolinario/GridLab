import { Component } from '@angular/core';
import { UploadButtonComponent } from "../upload-button/upload-button.component";
import { MergerComponent } from "../merger/merger.component";

@Component({
  selector: 'app-container',
  imports: [UploadButtonComponent, MergerComponent],
  templateUrl: './container.component.html',
  styleUrl: './container.component.scss'
})
export class ContainerComponent {

}
