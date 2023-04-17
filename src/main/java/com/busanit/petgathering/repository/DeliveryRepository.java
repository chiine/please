package com.busanit.petgathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
