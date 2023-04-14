package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.busanit.petgathering.entity.Dog;

public interface DogRepository extends JpaRepository<Dog, Long>{

	 
	@Query(value="select board.dog_id,img.img_url,board.name, board.age,board.content, board.gender,board.my_type,board.dog_type from gathering_board board left outer join dog_img img on board.dog_id = img.dog_id order by dog_id desc", nativeQuery=true)
	List<Dog> selectDogList();

}
