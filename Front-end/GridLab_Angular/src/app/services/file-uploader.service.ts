import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { FileEntity } from '../entities/FileEntity';
import { catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploaderService {
  private httpClient:HttpClient = inject(HttpClient);
  private apiUrl: string = "http://localhost:8080";

  postFile(formData: FormData):Observable<FileEntity>{
    return this.httpClient.post<FileEntity>(this.apiUrl + "/upload/divider/", formData).pipe(
      catchError((error) => { 
        alert(error.error)
        throw error;
      })
    );
  } 

  postFileList(formData: FormData):Observable<FileEntity>{
    console.log("I'm in service hiiii")
    return this.httpClient.post<FileEntity>(this.apiUrl + "/upload/merger/", formData).pipe(
      catchError((error) => { 
        alert(error.error)
        throw error;
      })
    );
  } 

  getFile(fileId: string): Observable<Blob>{
    return this.httpClient.get(this.apiUrl + fileId, {responseType: 'blob'});
  }
}
