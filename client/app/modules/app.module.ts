import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../components/app.component';
import { DayCardComponent } from '../components/dayCard.component';

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
