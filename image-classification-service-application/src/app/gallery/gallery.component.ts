import { Component, OnInit } from '@angular/core';
import {Image} from "../image";
import {HttpErrorResponse} from "@angular/common/http";
import {ImageService} from "../image.service";
import {map, Observable, of, startWith} from "rxjs";
import {Url} from "../url";
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import {Page} from "../page";
import {Description} from "../description";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit {

  images!: Image[];
  tag!: String;
  dontCallNgOnIt = false;
  descriptions: Description[] = [];
  formControl = new FormControl();
  autoFilter!: Observable<string[]>;
  tags : string[] = [];
  changeEvent : boolean = false;

  pages!: Page;
  numberOfImages!: number;
  pageNumber!: number;

  constructor(private imageService: ImageService, public router: Router, private route : ActivatedRoute) {
    console.log("maria2");
  }

  ngOnInit(): void {
    console.log("maria1");
    this.route.queryParams. subscribe (params => {
      this.readFromParams(params);
      console.log("maria8 " + Object.keys(params).length);
        if (Object.keys(params).length !== 0) {
          this.dontCallNgOnIt = true;
          console.log("maria5");
          this.getImagesByTag(this.tag);
        } else {
          this.router.navigate(['/gallery'])
        }
    })

    if(!this.dontCallNgOnIt) {
      this.initialise();
    }
  }

  initialise() {
    //this.getPaginatedImages();
    this.getImages();
    //this.autocomplete();
  }

  public readFromParams(params : any){
    this.route.queryParams.subscribe((params: any) => {
      this.tag = params.data;
      console.log("maria7" + this.tag);
    })
    console.log("maria6");
  }

  onSubmit(tag: String) {
    return this.getImagesByTag(tag);
  }

  public isEmpty(str : String) {
    return (!str || str.length === 0 );
  }

  public getImagesByTag(tag : String): void {
    let tags : String[] = [];
    var imageUrl = tag?.split(",", 2);
    let i = 0;
    while(!this?.isEmpty(imageUrl[i])) {
      tags[i] = imageUrl[i];
      console.log(tags[i]);
      i++;
    }
    this.imageService.getImagesByTag(tags).subscribe(
      (response: Image[]) => {
        this.images = response;
        this.images?.forEach((image) => {
          let url
          url = image.url;
          var imageUrl = url.split("=", 20);
         // console.log("MArche " + imageUrl[1]);
          image.url = imageUrl[1];
        });
        this.router.navigate(['/gallery'], {queryParams: {data: this.tag}});
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
  public notify(event : string) {
    if(event === undefined) {

    }
    else  {
      this.autocompleteWhenTyping(event);
    }
  }
  public getImages(): void {
    this.imageService.getImages().subscribe(
      (response: Image[]) => {
        this.images = response;
        this.images?.forEach((image) => {
          let url
          url = image.url;
          //console.log("MArche ");
          var imageUrl = url.split("=", 2);
          //console.log("MArche " + imageUrl[1]);
          image.url = imageUrl[1];
        });
      },
      (error: HttpErrorResponse) => {
        console.log("MARiddssA");
        alert(error.message);
      }
    )
  }

  public navigateToAddImage(id : number) {
    this.router.navigate(['/add-image'], { queryParams: {data:id }})
  }

  public getPaginatedImages() {
    let pageNumber = 0;
    let numberOfImages = 4;
    this.imageService.getPaginatedImages(pageNumber, numberOfImages)
      .subscribe((response: any) => {
        this.pages.image = new response.data;
      });

    console.log("maria stranici " + this.pages.image);
  }

  public autocomplete() {
    this.imageService.autocomplete().subscribe(
      (response: Description[]) => {
        this.descriptions = response;
        let i = 1;
        this.descriptions?.forEach((description) => {
          //console.log(description.tag);
          let desc = description.tag.toString();
          this.tags[i] = desc;
          i++;
        });
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public autocompleteWhenTyping(tagTyping : string) {
    this.imageService.autocompleteWhenTyping(tagTyping).subscribe(
      (response: Description[]) => {
        this.descriptions = response;

        let i = 1;
        this.descriptions?.forEach((description) => {
          let desc = description.tag.toString();
          this.tags[i] = desc;
          i++
        });
        this.autoFilter = this.formControl.valueChanges.pipe(
          startWith(''),
          map(value => this.mat_filter(value))
        );
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  private mat_filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.tags?.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
  }


}
