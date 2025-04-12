import { Component, inject } from '@angular/core';
import { FileUploaderService } from '../../services/file-uploader.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-upload-button',
  imports: [],
  templateUrl: './upload-button.component.html',
  styleUrl: './upload-button.component.scss'
})
export class UploadButtonComponent implements OnInit{
  private fileUploaderServ:FileUploaderService = inject(FileUploaderService);

  ngOnInit(): void {
  }

  OnFileUpload(event: any){

  }

}
