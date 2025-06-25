import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { FileEntity } from '../entities/FileEntity';
import { catchError, of } from 'rxjs';
import { map } from 'rxjs/operators';

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

  getAllFiles(): Observable<FileEntity[]> {
  return this.httpClient.get<any[]>(this.apiUrl + "/fileLib").pipe(
    map(files =>
      files.map(file => ({
        ...file,
        timeNDate: new Date(file.timeNDate.replace(' ', 'T'))  // <-- conversion
      }))
    )
  );
}

getAllFilesFromUser(userId: number): Observable<FileEntity[]> {
  return this.httpClient.get<any[]>(this.apiUrl + "/fileLib/" + userId).pipe(
    map(files =>
      files.map(file => ({
        ...file,
        timeNDate: new Date(file.timeNDate.replace(' ', 'T'))  // <-- conversion
      }))
    )
  );
}

logout(): Observable<any> {
  return this.httpClient.post(this.apiUrl + "logout", {});
}
}
