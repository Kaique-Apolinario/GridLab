import { Component, inject, OnInit } from '@angular/core';
import { FileEntity } from '../../entities/FileEntity';
import { FileUploaderService } from '../../services/file-uploader.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {CdkDrag, CdkDragDrop, CdkDropList, moveItemInArray} from '@angular/cdk/drag-drop';


@Component({
  selector: 'app-merger',
  imports: [CommonModule, FormsModule, CdkDrag, CdkDropList],
  templateUrl: './merger.component.html',
  styleUrl: './merger.component.scss'
})
export class MergerComponent implements OnInit{

  private fileUploaderServ:FileUploaderService = inject(FileUploaderService);

  formData!: FormData | null;
  fileName!: string;
  ignoreRepeatedRows: boolean = false;
  buttonMessage:string= "";
  downloadFile!:FileEntity | null ;
  allFiles!:File[];



  ngOnInit(): void {
  }

  onFileUpload(event: any){
    if(event.target.files.length > 0 ) {
       const inputFiles = event.target as HTMLInputElement;
       if (inputFiles.files){
       this.allFiles = Array.from(inputFiles.files);
      }
       this.downloadFile = null;
       this.formData = new FormData()
       this.filesToFormData();
      
    }
    }

    filesToFormData(){
      for (const file of this.allFiles) {
        if (file.name.toString().endsWith(".xlsx") || file.name.toString().endsWith(".xls")){
          this.formData!.append('files', file);
        } else {
          this.formData = null;
          alert("It isn't a .xlsx or a .xls file. Please, try again!");
        }
      }
    }

    onToggle(){
      this.downloadFile = null;
    }

    onFormsDone(){
      if (this.downloadFile == null){
      this.formData!.delete("ignoreRepeatedRows")
      this.formData!.append("ignoreRepeatedRows", this.ignoreRepeatedRows.toString());
      this.fileUploaderServ.postFileList(this.formData!).subscribe((fileDtoJson) => this.downloadFile = fileDtoJson);
    } else {
      this.onDownloadFile();
    }
  }

  onDownloadFile(){
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


    changeFileOrder(event: CdkDragDrop<File[]>) {
      moveItemInArray(this.allFiles,  event.previousIndex, event.currentIndex);
      this.formData = new FormData()
      this.filesToFormData();
      }
    }