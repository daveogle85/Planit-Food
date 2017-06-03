import { Component, Input } from '@angular/core';

@Component({
  selector: 'day-card',
  templateUrl: './dayCard.component.html',
  styleUrls: ['./dayCard.component.scss'],
})

export class DayCardComponent {
    @Input() public date: number;
    @Input() public mealList: string[];
}
