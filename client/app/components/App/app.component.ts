import { Component } from '@angular/core';
import { mockMealList } from '../DayCard/DayCard';
import {ViewEncapsulation} from '@angular/core'

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['../../../styles/styles.scss','./app.component.scss'],
})

export class AppComponent {
    public dayCards = mockMealList();
    public title = 'PlanIt Food';
}
