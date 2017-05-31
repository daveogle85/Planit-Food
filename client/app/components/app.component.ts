import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  templateUrl: '../templates/app.component.html',
  styleUrls: ['../styles/app.component.scss'],
})

export class AppComponent {
    public dateForDayCard = Date.now();
    public mealList: string[] = [
        'Roast Kitty Cat',
    ];
}
