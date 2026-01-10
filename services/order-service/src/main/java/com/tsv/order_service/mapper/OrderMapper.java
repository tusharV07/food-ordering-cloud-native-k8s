package com.tsv.order_service.mapper;

import org.mapstruct.Mapper;

import com.tsv.order_service.dto.OrderResponseDTO;
import com.tsv.order_service.entity.Order;

@Mapper(componentModel="spring")
public interface OrderMapper {
	OrderResponseDTO toDTO(Order order);
	Order toEntity(OrderResponseDTO orderResponse);
}
