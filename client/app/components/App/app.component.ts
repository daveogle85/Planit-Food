import { Component } from '@angular/core';
import {ViewEncapsulation} from '@angular/core'
import { View } from '../../enums';
import { HomeView, PlannerView } from './Views';

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['../../../styles/styles.scss','./app.component.scss'],
})

export class AppComponent {
    public title = 'PlanIt Food';
    public view = HomeView;

    public toggleButtonClick = () => {
        switch (this.view.value) {
            case View.HomeView:
                this.view = PlannerView;
                break;
            default:
                this.view = HomeView;
                break;
        }
    }
}
