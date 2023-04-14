package com.busanit.petgathering.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.busanit.petgathering.entity.MeetingBoard;

public interface MeetingBoardRepository extends JpaRepository<MeetingBoard, Long>,QuerydslPredicateExecutor<MeetingBoard>, MeetingMemberRepositoryCustom {

	public List<MeetingBoard> findAllByOrderByIdDesc();

	public Page<MeetingBoard> findByMemberNum(Long num, Pageable pageable);
}
