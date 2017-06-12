import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { CarouselComponent } from '../Carousel/carousel.component';
import { DayCardComponent } from '../DayCard/dayCard.component';
import 'jquery';
import 'owl.carousel/dist/owl.carousel.min';

describe('App', () => {

    let comp: AppComponent;
    let fixture: ComponentFixture<AppComponent>;
    let de: DebugElement;
    let el: HTMLElement;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [
                AppComponent,
                CarouselComponent,
                DayCardComponent
            ]
        });
        fixture = TestBed.createComponent(AppComponent);
        comp = fixture.componentInstance;
    });
    it('should create a new App Component', () => {
        expect(fixture.componentInstance instanceof AppComponent)
            .toBe(true, 'should create AppComponent');
    });

    it('should display the title', () => {
        de = fixture.debugElement.query(By.css('h1'));
        el = de.nativeElement;
        fixture.detectChanges();
        expect(el.textContent).toContain(comp.title);
    });

});
