import { FoodItem } from "../../shared/models/FoodItem";
import { Restaurant } from "../../shared/models/Restaurant";

export interface OrderDto {
    foodItems: FoodItem[];
    restaurant: Restaurant;
    userId: number;
}