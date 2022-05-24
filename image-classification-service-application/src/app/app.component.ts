import {Component, OnInit} from '@angular/core';
import {Image} from "./image";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ImageService} from "./image.service";
import {Observable} from "rxjs";
import {NavigationStart, Router} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public images: Image[] | undefined;
  public image: Image = new Image();
  showButton:boolean=false;
  lightSwitch = document.getElementById('lightSwitch') as HTMLInputElement;
  constructor (private imageService: ImageService, public router: Router) {
  }

  ngOnInit() : void {
    this.goToAnalysePage();
  }

  reloadCurrentPage() {
    this.router.navigate(['/gallery']);
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    //this.router.onSameUrlNavigation = 'reload';
  }


  public getImages(): void {
    this.imageService.getImages().subscribe(
      (response: Image[]) => {
        this.images = response;
      },
      (error: HttpErrorResponse) => {
        console.log("MARiA");
        alert(error.message);
      }
    )
  }

  goToAnalysePage() {
    this.router.navigate(['']);
  }

  public addImage(url : String): void{
    this.imageService.addImage(url).subscribe(

      (response: Image) => {
        this.image = response;
        console.log("MARA" + this.image);
        //this.goToAnalysePage();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        console.log("MARiA");
      }
    )
  }


}
