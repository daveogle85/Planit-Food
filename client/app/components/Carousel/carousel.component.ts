import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'carousel',
    templateUrl: './carousel.component.html',
    styleUrls: ['./carousel.component.scss'],
})

export class CarouselComponent implements OnInit {

    public ngOnInit() {
        $('.carousel-day-card').slick({
            centerMode: true,
            centerPadding: '160px',
            slidesToShow: 3,
            arrows: true,
            focusOnSelect: true,
            dots: true,
            responsive: [
                {
                    breakpoint: 768,
                    settings: {
                        arrows: true,
                        centerMode: true,
                        centerPadding: '40px',
                        slidesToShow: 3,
                    },
                },
                {
                    breakpoint: 480,
                    settings: {
                        arrows: false,
                        centerMode: true,
                        centerPadding: '40px',
                        slidesToShow: 1,
                    },
                },
            ],
        });
    }

}
