package com.busanit.petgathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberNum(Long memberNum);
}
