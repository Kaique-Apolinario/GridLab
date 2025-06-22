import { Component, inject, OnInit, signal } from '@angular/core';
import { FileUploaderService } from '../../services/file-uploader.service';
import { FileEntity } from '../../entities/FileEntity';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-lib',
  imports: [CommonModule],
  templateUrl: './file-lib.component.html',
  styleUrl: './file-lib.component.scss'
})
export class FileLibComponent implements OnInit{

  private fileService:FileUploaderService = inject(FileUploaderService);

  fileList = signal<FileEntity[]>([]);

  ngOnInit(): void {
    this.fileService.getAllFiles().subscribe((response) => {this.fileList.set(response)});
    console.log(this.fileList())
  }

}
