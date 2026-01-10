package com.tsv.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsv.order_service.dto.OrderRequestDTO;
import com.tsv.order_service.dto.OrderResponseDTO;
import com.tsv.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@CrossOrigin(origins="http://localhost:4200")
public class OrderController {
	private final OrderService orderSvc;
	
	@PostMapping("/saveOrder")
	public ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody OrderRequestDTO orderRequest){
		OrderResponseDTO savedOrder=orderSvc.saveOrder(orderRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
	}
}
