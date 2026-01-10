package com.tsv.foodcatalogue.dto;

import java.util.List;

import com.tsv.foodcatalogue.entity.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCataloguePageDTO {
	private List<FoodItemDTO> foodItems;
	private RestaurantDTO restaurantDto;
}
