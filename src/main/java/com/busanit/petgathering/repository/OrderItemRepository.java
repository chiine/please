package com.busanit.petgathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
