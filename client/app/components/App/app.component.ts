import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})

export class AppComponent {
    public dateForDayCard = Date.now();
    public mealList: string[] = [
        'Roast Kitty Cat',
        'Fish and Chips',
        'Sardines and Rice',
        'Roast Chicken with Eggs and Slippers and dog foot',
    ];
}
