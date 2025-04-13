import { Component, inject, OnInit } from '@angular/core';
import { FileUploaderService } from '../../services/file-uploader.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-upload-button',
  imports: [CommonModule, FormsModule],
  templateUrl: './upload-button.component.html',
  styleUrl: './upload-button.component.scss'
})
export class UploadButtonComponent implements OnInit{
  private fileUploaderServ:FileUploaderService = inject(FileUploaderService);
  formData!: FormData;
  fileName!: string;
  howManySheets!: number;
  header: boolean = false;

  ngOnInit(): void {
  }

  OnFileUpload(event: any){
    if(event.target.files.length > 0 ) {
      const file: File = event.target.files[0];
      this.fileName = file.name.toString();
      this.formData = new FormData();
      this.formData.append('file', file);
    }
  }

  OnFormsDone(){
    if (this.howManySheets > 2000000000){
      alert("More than 2 billions lines per sheet? Sheesh! Try a smaller number.")
    } else {
      this.formData.append('sheetParts', this.howManySheets.toString());
  }
      this.formData.append('header', this.header.toString());
      this.fileUploaderServ.postTasks(this.formData).subscribe();
  }
}
