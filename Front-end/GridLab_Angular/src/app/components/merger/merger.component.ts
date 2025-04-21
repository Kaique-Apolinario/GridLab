import { Component, inject, OnInit } from '@angular/core';
import { FileEntity } from '../../entities/FileEntity';
import { FileUploaderService } from '../../services/file-uploader.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-merger',
  imports: [CommonModule, FormsModule],
  templateUrl: './merger.component.html',
  styleUrl: './merger.component.scss'
})
export class MergerComponent implements OnInit{
  private fileUploaderServ:FileUploaderService = inject(FileUploaderService);

  formData!: FormData;
  fileName!: string;
  header: boolean = false;
  buttonMessage:string= "";
  downloadFile!:FileEntity | null ;




  ngOnInit(): void {
  }

  OnFileUpload(event: any){
    if(event.target.files.length > 0 ) {
      let allFiles = event.target.files;
        this.formData = new FormData()
      for (const file of allFiles) {
        
      if (file.name.toString().endsWith(".xlsx") || file.name.toString().endsWith(".xls")){
        this.formData.append('files', file);
      } else {
        alert("It isn't a .xlsx or a .xls file. Please, try again!");
      }

      }
    }
    }

    OnFormsDone(){
      this.fileUploaderServ.postFileList(this.formData).subscribe((fileDtoJson) => this.downloadFile = fileDtoJson);} 
    }