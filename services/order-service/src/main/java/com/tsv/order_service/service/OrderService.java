package com.tsv.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tsv.order_service.dto.OrderRequestDTO;
import com.tsv.order_service.dto.OrderResponseDTO;
import com.tsv.order_service.dto.UserDTO;
import com.tsv.order_service.entity.Order;
import com.tsv.order_service.mapper.OrderMapper;
import com.tsv.order_service.repository.OrderRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepo orderRepo;
	private final SequenceGenerator sequenceGen;
	private final OrderMapper orderMapper;
	private final WebClient webClient;
	

	public OrderResponseDTO saveOrder(OrderRequestDTO orderRequest) {
		Long orderId=sequenceGen.getNextSequence();
		UserDTO user=fetchUserById(orderRequest.getUserId());

		Order order=new Order(orderId,orderRequest.getFoodItems(),orderRequest.getRestaurant(),user);
		orderRepo.save(order);
		
		return orderMapper.toDTO(order);
	}

	private UserDTO fetchUserById(Integer userId) {
		return webClient.get()
				.uri("http://USERINFORMATION/api/v1/user/fetchUserById/"+userId)
				.retrieve()
				.bodyToMono(UserDTO.class)
				.block();
	}
}
