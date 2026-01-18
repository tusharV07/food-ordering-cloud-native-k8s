package com.tsv.order_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import com.tsv.order_service.dto.OrderRequestDTO;
import com.tsv.order_service.dto.OrderResponseDTO;
import com.tsv.order_service.dto.UserDTO;
import com.tsv.order_service.entity.Order;
import com.tsv.order_service.mapper.OrderMapper;
import com.tsv.order_service.repository.OrderRepo;

import reactor.core.publisher.Mono;

class OrderServiceTest {
	@Mock
	private OrderRepo orderRepo;

	@Mock
	private SequenceGenerator sequenceGenerator;

	@Mock
	private OrderMapper orderMapper;

	@Mock
	private WebClient webClient;

	@Mock
	WebClient.RequestHeadersUriSpec uriSpec;

	@Mock
	WebClient.RequestHeadersSpec headersSpec;

	@Mock
	WebClient.ResponseSpec responseSpec;

	@InjectMocks
	private OrderService orderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		when(webClient.get()).thenReturn(uriSpec);
		when(uriSpec.uri(anyString())).thenReturn(headersSpec);
		when(headersSpec.retrieve()).thenReturn(responseSpec);
		
	}

	@Test
	void saveOrderInDb_shouldSaveOrderAndReturnOrderDTO() {
		// Arrange
		OrderRequestDTO orderDetails = new OrderRequestDTO();
		Long newOrderId = 1L;
		UserDTO userDTO = new UserDTO();
		Order orderToBeSaved = new Order(newOrderId, orderDetails.getFoodItems(), orderDetails.getRestaurant(),
				userDTO);
		when(orderMapper.toDTO(orderToBeSaved)).thenReturn(new OrderResponseDTO(
				newOrderId,orderDetails.getFoodItems(),orderDetails.getRestaurant(),userDTO));
//		OrderResponseDTO orderDTOExpected = orderMapper.toDTO(orderToBeSaved);

		when(sequenceGenerator.getNextSequence()).thenReturn(newOrderId);
		when(responseSpec.bodyToMono(UserDTO.class)).thenReturn(Mono.just(userDTO));
		when(orderRepo.save(orderToBeSaved)).thenReturn(orderToBeSaved);

		// Act
		OrderResponseDTO orderDTOActual = orderService.saveOrder(orderDetails);

		// Assert
		verify(sequenceGenerator, times(1)).getNextSequence();
		assertThat(orderDTOActual.getUser()).isEqualTo(userDTO);
	}
}
