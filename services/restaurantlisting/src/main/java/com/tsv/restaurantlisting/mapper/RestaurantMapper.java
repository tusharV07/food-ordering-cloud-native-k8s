package com.tsv.restaurantlisting.mapper;

import org.mapstruct.Mapper;

import com.tsv.restaurantlisting.dto.RestaurantDTO;
import com.tsv.restaurantlisting.entity.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
	
	RestaurantDTO toDTO(Restaurant restaurant);
	Restaurant toEntity(RestaurantDTO restaurantDTO);
}
