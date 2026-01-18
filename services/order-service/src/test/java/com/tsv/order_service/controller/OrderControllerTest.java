package com.tsv.order_service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tsv.order_service.dto.OrderRequestDTO;
import com.tsv.order_service.dto.OrderResponseDTO;
import com.tsv.order_service.service.OrderService;

class OrderControllerTest {
	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void saveOrder_shouldReturnCreatedStatus() {
		// Arrange
		OrderRequestDTO orderDetails = new OrderRequestDTO();
		OrderResponseDTO orderSavedInDB = new OrderResponseDTO();
		when(orderService.saveOrder(orderDetails)).thenReturn(orderSavedInDB);

		// Act
		ResponseEntity<OrderResponseDTO> response = orderController.saveOrder(orderDetails);

		// Assert
		verify(orderService, times(1)).saveOrder(orderDetails);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getRestaurant()).isEqualTo(orderSavedInDB.getRestaurant());
	}
}
