package com.tsv.foodcatalogue.mapper;

import org.mapstruct.Mapper;

import com.tsv.foodcatalogue.dto.FoodItemDTO;
import com.tsv.foodcatalogue.entity.FoodItem;

@Mapper(componentModel="spring")
public interface FoodItemMapper {
	FoodItem toEntity(FoodItemDTO foodItemDto);
	FoodItemDTO toDto(FoodItem foodItem);
}
