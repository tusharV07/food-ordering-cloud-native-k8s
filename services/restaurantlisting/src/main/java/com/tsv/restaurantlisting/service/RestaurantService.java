package com.tsv.restaurantlisting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tsv.restaurantlisting.dto.RestaurantDTO;
import com.tsv.restaurantlisting.entity.Restaurant;
import com.tsv.restaurantlisting.mapper.RestaurantMapper;
import com.tsv.restaurantlisting.repo.RestaurantRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {
	private final RestaurantRepo restaurantRepo;
	private final RestaurantMapper restaurantMapper;

	public List<RestaurantDTO> findAllRestaurants() {
		List<Restaurant> restaurants=restaurantRepo.findAll();
		return restaurants.stream().map(restaurantMapper::toDTO).toList();
	}

	public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
		Restaurant restaurant=restaurantRepo.save(restaurantMapper.toEntity(restaurantDTO));
		return restaurantMapper.toDTO(restaurant);
	}

	public Optional<RestaurantDTO> fetchRestaurantById(Integer id) {
		Optional<Restaurant> restaurant= restaurantRepo.findById(id);
		return restaurant.map(restaurantMapper::toDTO);
	}
	
	
}
