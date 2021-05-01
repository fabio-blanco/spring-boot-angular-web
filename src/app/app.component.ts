import { Component, OnInit } from '@angular/core';
import { LanguageService } from "./language.service";
import { Language } from "./language";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Spring Boot Angular Web';

  languages: Language[] = [];

  constructor(private languageService: LanguageService) {
  }

  ngOnInit(): void {
    this.languageService.getLanguages().subscribe(data => {
      this.languages = data;
    });
  }

}
