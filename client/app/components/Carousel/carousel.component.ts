import {
    Component,
    Input,
    ElementRef,
    HostBinding,
    AfterViewInit,
    OnDestroy
} from '@angular/core';
import { DayCard } from '../DayCard/DayCard';

const owlThemes = [
    'owl-carousel',
    'owl-theme',
    'owl-centered'
];

@Component({
    selector: 'carousel',
    templateUrl: './carousel.component.html',
    styleUrls: ['./carousel.component.scss'],
})

export class CarouselComponent implements AfterViewInit, OnDestroy {
    @HostBinding('class') public defaultClass = owlThemes.join(' ');
    @Input() public dayCards: DayCard[];

    // Options for owl carousel
    private owlOptions = {
        center: true,
        items: 5,
        loop: true,
        margin: 10,
        nav: true,
        responsive: {
            600: {
                items: 3
            }
        }
    };

    private owlElement: any;
    private defaultOptions: any = {};

    constructor(private el: ElementRef) { }

    public ngAfterViewInit() {
        $('.owl-carousel')['owlCarousel'](this.owlOptions);
    }

    public ngOnDestroy() {
        this.owlElement.owlCarousel('destroy');
        this.owlElement = null;
    }
}
