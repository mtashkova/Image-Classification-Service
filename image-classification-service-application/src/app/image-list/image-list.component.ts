import { Component, OnInit } from '@angular/core';
import {Image} from "../image";
import {HttpErrorResponse} from "@angular/common/http";
import {ImageService} from "../image.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component-light.css']
})
export class ImageListComponent implements OnInit {
  public images!: Image[];
  public image: Image = new Image();
  public loading: boolean = false;
  public error: boolean = false;
  constructor (private imageService: ImageService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  public analyse() {
    if (this.image.url) {
      this.loading = true;
    }
  }

  public pingURL(url: string): string {
    let newUrl = "https://cors-anywhere.herokuapp.com/" + url;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", newUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    if(xmlHttp.status != 200) {
      return "Not a valid image URL!".toString();
    } else return ""
  }

  public isValidURL(url: string): boolean {
     let urlToCheck: URL;
    try {
      urlToCheck = new URL(url);
    } catch (_) {
      return false;
    }

    return urlToCheck.protocol === "http:" || urlToCheck.protocol === "https:";
}

  onSubmit(url: String) {
    return this.addImage(url);
  }

  public onClickNavigate() {
    this.router.navigate(['/add-image'], { queryParams: {data:this.image.url }});
  }
  public addImage(url : String): void{

    let isUrlValid = this.isValidURL(url.toString());

    if(isUrlValid) {
      this.imageService.addImage(url).subscribe(
        (response: Image) => {
          this.image = response;
          this.router.navigate(['/add-image'], {queryParams: {data: this.image.id}});
        },
        (error: HttpErrorResponse) => {
          this.error = true;
          this.router.navigate(['']);
        }
      )
    } else {
      console.log("Url is not valid!");
      this.router.navigate(['']);
    }
  }

}
