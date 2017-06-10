import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { OwlModule } from 'ng2-owl-carousel';

import { AppComponent } from '../components/App/app.component';
import { DayCardComponent } from '../components/DayCard/dayCard.component';
import { CarouselComponent } from '../components/Carousel/carousel.component';

import '../../styles/styles.scss';
import '../../styles/headings.css';

@NgModule({
    imports: [
        BrowserModule,
        OwlModule
    ],
    declarations: [
        AppComponent,
        DayCardComponent,
        CarouselComponent,
    ],
    bootstrap: [AppComponent],
})
export class AppModule { }
