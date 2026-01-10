package com.tsv.foodcatalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsv.foodcatalogue.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
	List<FoodItem> findByRestaurantId(Integer restaurantId);
}
