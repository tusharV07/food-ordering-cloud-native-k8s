package com.tsv.restaurantlisting.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsv.restaurantlisting.dto.RestaurantDTO;
import com.tsv.restaurantlisting.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	@GetMapping("/v1/restaurant/fetchAllRestaurants")
	public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurants(){
		List<RestaurantDTO> allRestaurants=restaurantService.findAllRestaurants();
		return ResponseEntity.ok(allRestaurants);
	}
	@PostMapping("/v1/restaurant/addRestaurant")
	public ResponseEntity<RestaurantDTO> saveRestaurant(@RequestBody RestaurantDTO restaurantDTO){
		RestaurantDTO restaurantAdded=restaurantService.addRestaurantInDB(restaurantDTO);
		URI location=URI.create("/api/v1/restaurant/restaurantById/"+restaurantAdded.getId());
		return ResponseEntity.created(location).body(restaurantAdded);
	}
	@GetMapping("/v1/restaurant/restaurantById/{id}")
	public ResponseEntity<RestaurantDTO> findRestaurantById(@PathVariable Integer id){
		Optional<RestaurantDTO> restaurantDTO= restaurantService.fetchRestaurantById(id);
		if(restaurantDTO.isPresent()) {
			return ResponseEntity.ok(restaurantDTO.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	
}
