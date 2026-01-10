package com.tsv.restaurantlisting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		private int id;
		private String name;
		private String address;
		private String city;
		private String restaurantDescription;
		
}