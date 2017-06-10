import { Component } from '@angular/core';
import * as $ from 'jquery';

@Component({
    selector: 'carousel',
    templateUrl: './carousel.component.html',
    styleUrls: ['./carousel.component.scss'],
})

export class CarouselComponent {
  public test: string[] = [
    'cat',
    'dog',
    'rabbit',
    'frog',
    'lizzard',
    'bat'
  ];
}
