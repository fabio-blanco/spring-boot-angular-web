import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Language} from "./language";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  private readonly languagesUrl: string;

  constructor(private http: HttpClient) {
    this.languagesUrl = 'http://localhost:8080/languages';
  }

  public getLanguages(): Observable<Language[]> {
    return this.http.get<Language[]>(this.languagesUrl);
  }
}
