package com.tsv.order_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tsv.order_service.entity.Order;

@Repository
public interface OrderRepo extends MongoRepository<Order, Long> {

}
