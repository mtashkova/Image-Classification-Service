import {Description} from "./description";

export class Image {
  id: number = 0;
  url: string = "";
  analysedTime: Date = new Date();
  service: string = "";
  width: number = 0;
  height: number = 0;
  descriptions:Description[] = [];
}

