package com.tsv.foodcatalogue.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDTO {
	private Long id;
	private String itemName;
	private String itemDescription;
	@JsonProperty("isVeg")
	private boolean veg;
	private BigDecimal price;
	private Integer restaurantId;
	private Integer quantity;
}
