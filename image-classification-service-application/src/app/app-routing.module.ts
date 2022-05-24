import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ImageListComponent} from "./image-list/image-list.component";
import {AddImageComponent} from "./add-image/add-image.component";
import {GalleryComponent} from "./gallery/gallery.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";

const routes: Routes = [
  {path: '', component: ImageListComponent},
  {path: 'add-image', component: AddImageComponent},
  {path: 'gallery', component: GalleryComponent},
  {path: '', redirectTo: '', pathMatch: 'full'},
  {path: 'add-image/:id', component: AddImageComponent},
  //{path: '/gallery?=', component: GalleryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
