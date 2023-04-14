package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.busanit.petgathering.dto.CommentDto;
import com.busanit.petgathering.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>,
QuerydslPredicateExecutor<Comment>,CommentRepositoryCustom{
	//@Query("select new com.busanit.petgathering.dto.CommentDto(c.id, c.content, c.writer) from Comment c where c.id = :id ")
	@Query("select new com.busanit.petgathering.dto.CommentDto(c.id, c.content) from Comment c where c.id = :id ")
	List<CommentDto> findAllByOrderByBoardIdDesc(Long id);
	
	
	@Modifying
	@Query("UPDATE Comment com set com.content = :content where com.idx = :id")
	void updateComment(Long id, String content);
	
	
	
}