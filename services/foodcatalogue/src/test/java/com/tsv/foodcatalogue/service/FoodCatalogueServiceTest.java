package com.tsv.foodcatalogue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import com.tsv.foodcatalogue.dto.FoodItemDTO;
import com.tsv.foodcatalogue.dto.RestaurantDTO;
import com.tsv.foodcatalogue.entity.FoodItem;
import com.tsv.foodcatalogue.mapper.FoodItemMapper;
import com.tsv.foodcatalogue.repository.FoodItemRepository;

import reactor.core.publisher.Mono;

class FoodCatalogueServiceTest {
	@Mock
	private FoodItemRepository foodItemRepo;

	@Mock
	WebClient.Builder webClientBuilder;

	@Mock
	private WebClient webClient;

	@Mock
	WebClient.RequestHeadersUriSpec uriSpec;

	@Mock
	WebClient.RequestHeadersSpec headersSpec;

	@Mock
	WebClient.ResponseSpec responseSpec;

	@Mock
	private FoodItemMapper mapper;

	@InjectMocks
	private FoodCatalogueService foodCatalogueService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
		when(webClientBuilder.build()).thenReturn(webClient);
		when(webClient.get()).thenReturn(uriSpec);
		when(uriSpec.uri(anyString())).thenReturn(headersSpec);
		when(headersSpec.retrieve()).thenReturn(responseSpec);
	}

	@Test
	void addFoodItem_ShouldSaveFoodItemAndReturnMappedDTO() {
		// Arrange
		FoodItemDTO foodItemDTO = new FoodItemDTO();
		FoodItem foodItem = new FoodItem();
		when(foodItemRepo.save((FoodItem) any(FoodItem.class))).thenReturn(foodItem);
		when(mapper.toDto(foodItem)).thenReturn(foodItemDTO);
		when(mapper.toEntity(foodItemDTO)).thenReturn(foodItem);
		// Act
		FoodItemDTO result = foodCatalogueService.saveItem(foodItemDTO);

		// Assert
		verify(foodItemRepo, times(1)).save((FoodItem) any(FoodItem.class));
		assertThat(result.getItemDescription()).isEqualTo(foodItem.getItemDescription());
	}
	
//	@Test
//	void getRestaurantInfo_ShouldFetchRestaurantDTO() {
//		//Arrange
//		Integer restaurantId=1;
//		RestaurantDTO restaurantDTO=new RestaurantDTO(1,"restaurant 1","address 1","city 1","desc 1");
//		when(responseSpec.bodyToMono(RestaurantDTO.class)).thenReturn(Mono.just(restaurantDTO));
//		
//		//Act
//		RestaurantDTO result=foodCatalogueService.getRestaurantInfo(restaurantId);
//		
//		//Assert
//		assertThat(result.getName()).isEqualTo(restaurantDTO.getName());
//	}

}
