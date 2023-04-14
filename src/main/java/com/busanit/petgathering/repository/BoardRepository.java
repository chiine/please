package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	public List<Board> findAllByOrderByIdDesc();
	
	List<Board> findById(String id);
	
	Page<Board> findByMemberNum(Long memberNum,Pageable pageable);


}
