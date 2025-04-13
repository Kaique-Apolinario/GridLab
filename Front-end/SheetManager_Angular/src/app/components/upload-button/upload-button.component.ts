import { Component, inject, OnInit } from '@angular/core';
import { FileUploaderService } from '../../services/file-uploader.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FileEntity} from '../../entities/FileEntity';

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

  downloadFile!:FileEntity;


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
      this.fileUploaderServ.postFile(this.formData).subscribe((fileDtoJson) => this.downloadFile = fileDtoJson);

  }

  OnDownloadFile(){
    console.log("Downloading .......")
    console.log(this.downloadFile)
    this.fileUploaderServ.getFile(this.downloadFile.dlUrl).subscribe((blob) => {
      const objectUrl = URL.createObjectURL(blob);

      const a = document.createElement("a");
      a.href = objectUrl;
      a.download = this.downloadFile.fileName;
      a.click();

      URL.revokeObjectURL(objectUrl);

  });
  }
}
