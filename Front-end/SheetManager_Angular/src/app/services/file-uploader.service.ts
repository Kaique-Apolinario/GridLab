import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { FileEntity } from '../entities/FileEntity';

@Injectable({
  providedIn: 'root'
})
export class FileUploaderService {
  private httpClient:HttpClient = inject(HttpClient);
  private apiUrl: string = "http://localhost:8080";

  postFile(formData: FormData):Observable<FileEntity>{
    console.log("Ok")
    return this.httpClient.post<FileEntity>(this.apiUrl + "/upload", formData);
  } 

  getFile(fileId: string): Observable<Blob>{
    return this.httpClient.get(this.apiUrl + fileId, {responseType: 'blob'});
  }
}
