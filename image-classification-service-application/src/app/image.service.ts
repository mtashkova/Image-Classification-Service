import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Image} from "./image";
import {environment} from "../environments/environment";
import {Description} from "./description";

@Injectable({providedIn: 'root'})
export class ImageService {

  private apiServerUrl = environment.apiBaseUrl;
  constructor (private http: HttpClient) { }
  public getImages(): Observable<Image[]> {
    return this.http.get<Image[]>(`${this.apiServerUrl}`);
  }

  public addImage(url: String): Observable<Image> {
    return this.http.post<Image>(`${this.apiServerUrl}`, url);
  }

  public getImageById(imageId: number): Observable<Image> {
    return this.http.get<Image>(`${this.apiServerUrl}/${imageId}`);
  }

  public deleteImages(imageId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${imageId}`);
  }

  public getImagesByTag(tag : String[]) : Observable<Image[]> {
    return this.http.get<Image[]>(`${this.apiServerUrl}/images?tags=${tag}`);
  }

  public getPaginatedImages(page: number, imagesOnPage : number){
    return this.http.get<any>(`${this.apiServerUrl}/pagination?numimages=${imagesOnPage}&pagenumber=${page}`);
  }

  public autocomplete() : Observable<Description[]> {
    return this.http.get<Description[]>(`${this.apiServerUrl}/tags`);
  }
  public autocompleteWhenTyping(tag : string) : Observable<Description[]> {
    return this.http.get<Description[]>(`${this.apiServerUrl}/tags/tags?autocomplete=${tag}`);
  }
}
