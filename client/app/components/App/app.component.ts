import { Component } from '@angular/core';
import { mockMealList } from '../DayCard/DayCard';

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})

export class AppComponent {
    public dayCards = mockMealList();
    public title = 'PlanIt Food';
}
