package com.busanit.petgathering.service;

import com.busanit.petgathering.dto.DeliveryDto;
import com.busanit.petgathering.entity.Delivery;
import com.busanit.petgathering.entity.Order;
import com.busanit.petgathering.repository.DeliveryRepository;
import com.busanit.petgathering.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final OrderRepository orderRepository;

    public Long saveDelivery(DeliveryDto deliveryDto, Long orderId) throws Exception{
        Order order = orderRepository.getById(orderId);
        Delivery delivery = Delivery.createDelivery(deliveryDto,order);

        deliveryRepository.save(delivery);

        return delivery.getId();
    }

    public List<Delivery> findDelivery(){
        return deliveryRepository.findAll();
    }
    
    
    

}
