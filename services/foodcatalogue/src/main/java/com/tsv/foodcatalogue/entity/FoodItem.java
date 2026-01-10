package com.tsv.foodcatalogue.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded =true)
@Entity
public class FoodItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@EqualsAndHashCode.Include
	private Long id;
	private String itemName;
	private String itemDescription;
	@JsonProperty("isVeg")
	private boolean veg;
	private BigDecimal price;
	private Integer restaurantId;
	@Column(nullable=false, columnDefinition="INT DEFAULT 0")
	private Integer quantity;
}
