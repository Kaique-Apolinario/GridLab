import { Component, inject, OnInit, signal } from '@angular/core';
import { FileUploaderService } from '../../services/file-uploader.service';
import { FileEntity } from '../../entities/FileEntity';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { error } from 'node:console';

@Component({
  selector: 'app-file-lib',
  imports: [CommonModule],
  templateUrl: './file-lib.component.html',
  styleUrl: './file-lib.component.scss'
})
export class FileLibComponent implements OnInit{

  private fileService:FileUploaderService = inject(FileUploaderService);
  private router:Router = inject(Router);

  fileList = signal<FileEntity[]>([]);

  ngOnInit(): void {
    if (typeof (Number(this.router.url.replace("/fileLib/", ""))) === 'number'){
    const userIdFromUrl = Number(this.router.url.replace("/fileLib/", ""))
    this.fileService.getAllFilesFromUser(userIdFromUrl).subscribe((response) => {this.fileList.set(response)},
  error => this.router.navigate(['/']));
    }
    this.fileService.getAllFiles().subscribe((response) => {this.fileList.set(response)});
  }

}
