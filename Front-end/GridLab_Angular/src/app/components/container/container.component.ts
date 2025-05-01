import { Component } from '@angular/core';
import { UploadButtonComponent } from "../upload-button/upload-button.component";
import { MergerComponent } from "../merger/merger.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-container',
  imports: [UploadButtonComponent, MergerComponent, CommonModule, FormsModule],
  templateUrl: './container.component.html',
  styleUrl: './container.component.scss'
})
export class ContainerComponent {
  breakOrMerge: boolean = true;
}
