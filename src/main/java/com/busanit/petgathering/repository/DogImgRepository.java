package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.DogImg;

public interface DogImgRepository extends JpaRepository<DogImg, Long> {
	 
	List<DogImg> findByDogIdOrderByIdAsc(Long dogId);
}
