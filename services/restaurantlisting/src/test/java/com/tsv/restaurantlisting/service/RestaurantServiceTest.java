package com.tsv.restaurantlisting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tsv.restaurantlisting.dto.RestaurantDTO;
import com.tsv.restaurantlisting.entity.Restaurant;
import com.tsv.restaurantlisting.mapper.RestaurantMapper;
import com.tsv.restaurantlisting.repo.RestaurantRepo;

class RestaurantServiceTest {
	@Mock
	RestaurantRepo restaurantRepo;
	
	@Mock
	RestaurantMapper mapper;

	@InjectMocks
	RestaurantService restaurantService;
	

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAllRestaurants() {
		// Create mock restaurants
		List<Restaurant> mockRestaurants = Arrays.asList(
				new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"),
				new Restaurant(2, "Restaurant 2", "Address 2", "city 2", "Desc 2"));
		when(restaurantRepo.findAll()).thenReturn(mockRestaurants);

		// Call the service method
		List<RestaurantDTO> restaurantDTOList = restaurantService.findAllRestaurants();

		// Verify the result
		assertThat(restaurantDTOList).hasSameSizeAs(mockRestaurants);
		for (int i = 0; i < mockRestaurants.size(); i++) {
			RestaurantDTO expectedDTO = mapper.toDTO(mockRestaurants.get(i));
			assertThat(restaurantDTOList.get(i)).isEqualTo(expectedDTO);
		}

		// Verify that the repository method was called
		verify(restaurantRepo, times(1)).findAll();
	}

	@Test
	void testAddRestaurantInDB() {
		// Create a mock restaurant to be saved
		RestaurantDTO mockRestaurantDTO = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");
		Restaurant mockRestaurant = mapper.toEntity(mockRestaurantDTO);

		// Mock the repository behavior
		when(restaurantRepo.save(mockRestaurant)).thenReturn(mockRestaurant);
		when(mapper.toDTO(mockRestaurant))
        .thenReturn(new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"));
		// Call the service method
		RestaurantDTO savedRestaurantDTO = restaurantService.addRestaurantInDB(mockRestaurantDTO);

		// Verify the result
		assertThat(savedRestaurantDTO.getName()).isEqualTo(mockRestaurantDTO.getName());

		// Verify that the repository method was called
		verify(restaurantRepo, times(1)).save(mockRestaurant);
	}

	@Test
	void testFetchRestaurantById_ExistingId() {
		// Create a mock restaurant ID
		Integer mockRestaurantId = 1;

		// Create a mock restaurant to be returned by the repository
		Restaurant mockRestaurant = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Desc 1");

		// Mock the repository behavior
		when(restaurantRepo.findById(mockRestaurantId)).thenReturn(Optional.of(mockRestaurant));
		when(mapper.toDTO(mockRestaurant))
        .thenReturn(new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Desc 1"));
		// Call the service method
		Optional<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

		// Verify the response
		assertThat(response.get().getName()).isEqualTo(mockRestaurant.getName());
		assertThat(response.get().getId()).isEqualByComparingTo(mockRestaurantId);

		// Verify that the repository method was called
		verify(restaurantRepo, times(1)).findById(mockRestaurantId);
	}

	@Test
	void testFetchRestaurantById_NonExistingId() {
		// Create a mock non-existing restaurant ID
		Integer mockRestaurantId = 1;

		// Mock the repository behavior
		when(restaurantRepo.findById(mockRestaurantId)).thenReturn(Optional.empty());

		// Call the service method
		Optional<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

		// Verify the response
		assertThatThrownBy(response::get).isInstanceOf(NoSuchElementException.class);
		assertThat(response).isEmpty();

		// Verify that the repository method was called
		verify(restaurantRepo, times(1)).findById(mockRestaurantId);
	}

}
