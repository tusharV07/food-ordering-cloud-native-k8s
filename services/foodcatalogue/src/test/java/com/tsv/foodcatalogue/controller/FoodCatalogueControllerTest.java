package com.tsv.foodcatalogue.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tsv.foodcatalogue.dto.FoodItemDTO;
import com.tsv.foodcatalogue.service.FoodCatalogueService;

class FoodCatalogueControllerTest {
	@Mock
	private FoodCatalogueService foodCatalogueService;

	@InjectMocks
	private FoodCatalogueController foodCatalogueController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void addFoodItem_ShouldReturnCreatedStatus() {
		// Arrange
		FoodItemDTO foodItemDTO = new FoodItemDTO();
		when(foodCatalogueService.saveItem((FoodItemDTO) any(FoodItemDTO.class))).thenReturn(foodItemDTO);

		// Act
		ResponseEntity<FoodItemDTO> response = foodCatalogueController.saveFoodItem(foodItemDTO);

		// Assert
		verify(foodCatalogueService, times(1)).saveItem(foodItemDTO);
		assert response.getStatusCode() == HttpStatus.CREATED;
		assert response.getBody() == foodItemDTO;
	}
}
