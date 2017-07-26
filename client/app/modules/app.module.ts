import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from '../components/App/app.component';
import { DayCardComponent } from '../components/DayCard/dayCard.component';
import { CarouselComponent } from '../components/Carousel/carousel.component';

import '../../styles/styles.scss';

@NgModule({
    imports: [
        BrowserModule
    ],
    declarations: [
        AppComponent,
        DayCardComponent,
        CarouselComponent,
    ],
    bootstrap: [AppComponent],
})
export class AppModule { }
