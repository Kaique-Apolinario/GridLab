import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class FileUploaderService {
  private httpClient:HttpClient = inject(HttpClient);
private apiUrl: string = "http://localhost:8080";

  postTasks(formData: FormData):Observable<FormData>{
    console.log("Ok")
    return this.httpClient.post<FormData>(this.apiUrl + "/upload", formData);
  } 
}
