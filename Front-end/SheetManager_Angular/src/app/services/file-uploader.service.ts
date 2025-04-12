import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileUploaderService {
  private httpClient:HttpClient = inject(HttpClient);

}
