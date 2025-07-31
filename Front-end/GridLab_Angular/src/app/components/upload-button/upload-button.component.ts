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
  buttonMessage:string= "";
  downloadFile!:FileEntity | null ;
  file!: File | null ;


  ngOnInit(): void {
  }

  OnFileUpload(event: any){
    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) {
      return;
    }

    if(input.files.length > 0) {
      this.file = input.files[0];

      if (this.file !== null && (this.file.name.toString().endsWith(".xlsx") || this.file.name.toString().endsWith(".xls"))){
      this.fileName = this.file.name.toString();
      this.formData = new FormData();
      this.formData.append('file', this.file);
      } else {
        this.file = null;
        alert("It isn't a .xlsx or a .xls file. Please, try again!")
      }
      input.value = "";
    }
  }

  OnFormsDone(){
    if (this.downloadFile == null){
      if (this.howManySheets > 2000000000){
        alert("More than 2 billions lines per sheet? Sheesh! Try a smaller number.")
      } else {
        this.formData.delete('sheetParts');
        this.formData.append('sheetParts', this.howManySheets.toString());
      }
    this.formData.delete('header');
    this.formData.append('header', this.header.toString());
    this.fileUploaderServ.postFile(this.formData).subscribe((fileDtoJson) => this.downloadFile = fileDtoJson);} 
    else {
    this.OnDownloadFile();
    }
  }

  OnDownloadFile(){
    if (this.downloadFile != null) {
      this.fileUploaderServ.getFile(this.downloadFile.dlUrl).subscribe((blob) => {
        const objectUrl = URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = objectUrl;
        if (this.downloadFile != null) {
        a.download = this.downloadFile.fileName;
        a.click();

        URL.revokeObjectURL(objectUrl);
        }
  })}
  }
}
