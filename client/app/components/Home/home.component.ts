import { Component } from '@angular/core';
import { mockMealList } from '../DayCard/DayCard';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})

export class HomeComponent {
    public dayCards = mockMealList();
}
