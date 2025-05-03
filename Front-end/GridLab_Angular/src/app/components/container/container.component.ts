import { Component, OnInit, Signal, signal } from '@angular/core';
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
export class ContainerComponent implements OnInit{
  screenWidth = signal(0);
  fullOrPartialDisplay: string = "";
  breakOrMerge: boolean = false;
  
  ngOnInit(): void {
    if (typeof window !== 'undefined') {
      this.screenWidth.set(window.innerWidth);
      window.addEventListener("resize", () => {
        this.screenWidth.set(window.innerWidth);
      })
    }
  }

}
