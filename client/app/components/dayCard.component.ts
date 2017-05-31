import { Component, Input } from '@angular/core';

@Component({
  selector: 'day-card',
  templateUrl: '../templates/dayCard.component.html',
  styleUrls: ['../styles/dayCard.component.scss'],
})

export class DayCardComponent {
    @Input() public date: number;
    @Input() public mealList: string[];
}
