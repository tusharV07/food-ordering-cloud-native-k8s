package com.tsv.order_service.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.tsv.order_service.dto.FoodItemDTO;
import com.tsv.order_service.dto.RestaurantDTO;
import com.tsv.order_service.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="order")
public class Order {
	Long orderId;
	private List<FoodItemDTO> foodItems;
	private RestaurantDTO restaurant;
	private UserDTO user;
}
