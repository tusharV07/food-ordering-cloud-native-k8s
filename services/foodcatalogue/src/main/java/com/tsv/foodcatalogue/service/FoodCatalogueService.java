package com.tsv.foodcatalogue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tsv.foodcatalogue.dto.FoodCataloguePageDTO;
import com.tsv.foodcatalogue.dto.FoodItemDTO;
import com.tsv.foodcatalogue.dto.RestaurantDTO;
import com.tsv.foodcatalogue.entity.FoodItem;
import com.tsv.foodcatalogue.mapper.FoodItemMapper;
import com.tsv.foodcatalogue.repository.FoodItemRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodCatalogueService {
	private final FoodItemRepository foodItemRepo;
	private final FoodItemMapper foodItemMapper;
	private final WebClient.Builder webClientBuilder;
	private WebClient webClient;
	
	@PostConstruct
	void init() {
		webClient=webClientBuilder.baseUrl("http://RESTAURANT-SERVICE").build();
	}

	public FoodItemDTO saveItem(FoodItemDTO foodItemDto) {
		FoodItem savedItem=foodItemRepo.save(foodItemMapper.toEntity(foodItemDto));
		return foodItemMapper.toDto(savedItem);
	}

	public FoodCataloguePageDTO getRestaurantMenu(Integer restaurantId) {
		List<FoodItemDTO> foodItems=getRestaurantFoodItems(restaurantId);
		RestaurantDTO restaurantInfo=getRestaurantInfo(restaurantId);
		return createRestaurantMenu(foodItems,restaurantInfo);
	}

	private FoodCataloguePageDTO createRestaurantMenu(List<FoodItemDTO> foodItems, RestaurantDTO restaurantInfo) {
		FoodCataloguePageDTO restaurantMenu=new FoodCataloguePageDTO();
		restaurantMenu.setFoodItems(foodItems);
		restaurantMenu.setRestaurantDto(restaurantInfo);
		return restaurantMenu;
	}

	private RestaurantDTO getRestaurantInfo(Integer restaurantId) {
		return webClient.get()
				.uri("/api/v1/restaurant/restaurantById/"+restaurantId)
				.retrieve()
				.bodyToMono(RestaurantDTO.class)
				.block();
	}

	private List<FoodItemDTO> getRestaurantFoodItems(Integer restaurantId) {
		return foodItemRepo.findByRestaurantId(restaurantId).stream().map(foodItemMapper::toDto).toList();
	}
}
