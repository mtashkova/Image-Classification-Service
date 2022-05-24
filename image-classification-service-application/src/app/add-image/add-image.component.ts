import {Component, Input, OnInit} from '@angular/core';
import {Image} from "../image";
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {ImageService} from "../image.service";
import {ImageListComponent} from "../image-list/image-list.component";
import {Description} from "../description";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-add-image',
  templateUrl: './add-image.component.html',
  styleUrls: ['./add-image.component.css']
})
export class AddImageComponent implements OnInit {

  image: Image | undefined = new Image();
  images: Image[] | undefined;
  urls: String[] | undefined;
  constructor(private imageService: ImageService, private router: Router, private route : ActivatedRoute) {
    this.route.queryParams.subscribe((params: any) => {
      console.log(params);
      this.id = params.data;
    })
  }
  id!: number;
  ngOnInit(): void {
    if(this.id!=0) return this.getImageById(this.id)
  }

  public getImageById(id : number): void {
    this.imageService.getImageById(id).subscribe(
      (response: Image) => {
        this.image = response;
          let url
          url = this.image.url;
          this.image.analysedTime = new Date(this.image.analysedTime);
          var imageUrl = url.split("=", 2);
          this.image.url = imageUrl[1];
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
  public navigateToGalleryByTag(tag : String) {
    this.router.navigate(['/gallery'], { queryParams: {data:tag }})
  }
  onSubmit() {
  }

}
