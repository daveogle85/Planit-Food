import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../components/App/app.component';
import { DayCardComponent } from '../components/DayCard/dayCard.component';

@NgModule({
  imports: [
    BrowserModule,
  ],
  declarations: [
    AppComponent,
    DayCardComponent,
  ],
  bootstrap: [ AppComponent ],
})
export class AppModule { }
