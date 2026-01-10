import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FoodItemService } from '../service/fooditem.service';
import { CommonModule } from '@angular/common';
import { FoodCataloguePage } from '../../shared/models/FoodCataloguePage';
import { FoodItem } from '../../shared/models/FoodItem';
import { Restaurant } from '../../shared/models/Restaurant';


@Component({
  selector: 'app-food-catalogue',
  imports: [CommonModule],
  templateUrl: './food-catalogue.component.html',
  styleUrl: './food-catalogue.component.css'
})
export class FoodCatalogueComponent {
  restaurantId!: number;
  foodItemResponse!: FoodCataloguePage;
  foodItemCart: FoodItem[] = [];
  orderSummary!: FoodCataloguePage;


  constructor(private route: ActivatedRoute, private foodItemService: FoodItemService, private router: Router) {
  }

  ngOnInit() {

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.restaurantId = Number(id);
      }
    });

    this.getFoodItemsByRestaurant(this.restaurantId);

  }

  getFoodItemsByRestaurant(restaurant: number) {
    this.foodItemService.getFoodItemsByRestaurant(restaurant).subscribe(
      data => {
        console.log(data);
        this.foodItemResponse = data;
      }
    )
  }

  increment(food: FoodItem) {
    food.quantity++;
    const index = this.foodItemCart.findIndex(item => item.id === food.id);
    if (index === -1) {
      this.foodItemCart.push(food);
    } else {
      this.foodItemCart[index] = food;
    }
  }

  decrement(food: FoodItem) {
    if (food.quantity > 0) {
      food.quantity--;

      const index = this.foodItemCart.findIndex(item => item.id === food.id);
      if (this.foodItemCart[index].quantity == 0) {
        this.foodItemCart.splice(index, 1);
      } else {
        this.foodItemCart[index] = food;
      }

    }
  }

  onCheckOut() {
    this.foodItemCart;
    this.orderSummary = {
      foodItems: [],
      restaurantDto: new Object() as Restaurant
    }
    this.orderSummary.foodItems = this.foodItemCart;
    this.orderSummary.restaurantDto = this.foodItemResponse.restaurantDto;
    this.router.navigate(['/orderSummary'], { queryParams: { data: JSON.stringify(this.orderSummary) } });
  }

}
