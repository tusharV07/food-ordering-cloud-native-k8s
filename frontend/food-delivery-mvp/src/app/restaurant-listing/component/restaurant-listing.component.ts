import { Component } from '@angular/core';
import { Restaurant } from '../../shared/models/Restaurant';
import { Router } from '@angular/router';
import { RestaurantService } from '../service/restaurant.service';
import { CommonModule } from '@angular/common';

interface RestaurantEnh extends Restaurant {
  image:string;
  rating:number;
  ratingCount:number;
}

@Component({
  selector: 'app-restaurant-listing',
  imports: [CommonModule],
  templateUrl: './restaurant-listing.component.html',
  styleUrl: './restaurant-listing.component.css'
})
export class RestaurantListingComponent {
public restaurantList!: RestaurantEnh[];

arr = [3, 3.5, 4, 4.5, 5];


  ngOnInit() {
    this.getAllRestaurants();
  }

  constructor(private router: Router, private restaurantService: RestaurantService) { }

  getAllRestaurants() {
    this.restaurantService.getAllRestaurants().subscribe(
      data => {
        this.restaurantList = data.map((r: Restaurant)=> ({
          ...r,
          image: `assets/restaurant-pics/${this.getRandomImage()}`,
          rating: this.getrestaurantRating(),
          ratingCount: this.getrestaurantRatingCount()
        }));
      }
    )
  }
  getRandomNumber(min: number, max: number): number {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }


  getRandomImage(): string {
    const imageCount = 8;
    const randomIndex = this.getRandomNumber(1, imageCount);
    return `${randomIndex}.jpg`; 
  }

  getrestaurantRatingCount(): number {
    return Math.floor(Math.random() * (2000 - 15 + 1)) + 15;
  }

  getrestaurantRating(): number {
    return this.arr[Math.floor(Math.random() * this.arr.length)];
  }

  onButtonClick(id: number) {
    this.router.navigate(['/food-catalogue', id]);
  }
}
