package com.tsv.foodcatalogue.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsv.foodcatalogue.dto.FoodCataloguePageDTO;
import com.tsv.foodcatalogue.dto.FoodItemDTO;
import com.tsv.foodcatalogue.service.FoodCatalogueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/foodCatalogue")
@CrossOrigin(origins="http://localhost:4200")
public class FoodCatalogueController {
	private final FoodCatalogueService foodCatalogueService;
	
	@PostMapping("/saveFoodItem")
	public ResponseEntity<FoodItemDTO> saveFoodItem(@RequestBody FoodItemDTO foodItemDto){
		FoodItemDTO savedItem=foodCatalogueService.saveItem(foodItemDto);
		URI uri=URI.create("/api/v1/foodCatalogue/getFoodItem/"+savedItem.getId());
		return ResponseEntity.created(uri).body(savedItem);
	}
	
	@GetMapping("/getRestaurantMenuById/{restaurantId}")
	public ResponseEntity<FoodCataloguePageDTO> getRestuarantMenuById(@PathVariable Integer restaurantId){
		FoodCataloguePageDTO restaurantMenu=foodCatalogueService.getRestaurantMenu(restaurantId);
		return ResponseEntity.ok(restaurantMenu);
	} 
	
}
