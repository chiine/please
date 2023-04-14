package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	public List<Notice> findAllByOrderByIdDesc();
	
	List<Notice> findById(String id);

}
