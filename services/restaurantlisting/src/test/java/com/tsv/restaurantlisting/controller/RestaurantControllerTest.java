package com.tsv.restaurantlisting.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tsv.restaurantlisting.dto.RestaurantDTO;
import com.tsv.restaurantlisting.service.RestaurantService;

class RestaurantControllerTest {
	@InjectMocks
	RestaurantController restaurantController;

	@Mock
	RestaurantService restaurantService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFetchAllRestaurants() {
		// Mock the service behavior
		List<RestaurantDTO> mockRestaurants = Arrays.asList(
				new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
				new RestaurantDTO(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));
		when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);

		// Call the controller method
		ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurants();

		// Verify the response
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(mockRestaurants);
		// Verify that the service method was called
		verify(restaurantService, times(1)).findAllRestaurants();
	}

	@Test
	void testSaveRestaurant() {
		// Create a mock restaurant to be saved
		RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

		// Mock the service behavior
		when(restaurantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

		// Call the controller method
		ResponseEntity<RestaurantDTO> response = restaurantController.saveRestaurant(mockRestaurant);

		// Verify the response
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(mockRestaurant);

		// Verify that the service method was called
		verify(restaurantService, times(1)).addRestaurantInDB(mockRestaurant);
	}

	@Test
	void testFindRestaurantById() {
		// Create a mock restaurant ID
		Integer mockRestaurantId = 1;

		// Create a mock restaurant to be returned by the service
		RestaurantDTO mockRestaurant = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

		// Mock the service behavior
		when(restaurantService.fetchRestaurantById(mockRestaurantId)).thenReturn(Optional.ofNullable(mockRestaurant));

		// Call the controller method
		ResponseEntity<RestaurantDTO> response = restaurantController.findRestaurantById(mockRestaurantId);

		// Verify the response
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(mockRestaurant);

		// Verify that the service method was called
		verify(restaurantService, times(1)).fetchRestaurantById(mockRestaurantId);
	}
}
