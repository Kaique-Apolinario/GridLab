import { Component, inject, OnInit } from '@angular/core';
import { FileUploaderService } from '../../services/file-uploader.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FileEntity} from '../../entities/FileEntity';
import { MenuComponent } from "../menu/menu.component";

@Component({
  standalone: true,
  selector: 'app-upload-button',
  imports: [CommonModule, FormsModule, MenuComponent],
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


  ngOnInit(): void {
  }

  OnFileUpload(event: any){
    if(event.target.files.length > 0 ) {
      let file: File = event.target.files[0];
      if (file.name.toString().endsWith(".xlsx") || file.name.toString().endsWith(".xls")){
      this.fileName = file.name.toString();
      this.formData = new FormData();
      this.formData.append('file', file);
      } else {
        alert("It isn't a .xlsx or a .xls file. Please, try again!")
      }
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
