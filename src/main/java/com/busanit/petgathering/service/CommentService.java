package com.busanit.petgathering.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busanit.petgathering.dto.CommentDto;
import com.busanit.petgathering.entity.Comment;
import com.busanit.petgathering.repository.BoardRepository;
import com.busanit.petgathering.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService{

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	public void CommentInsert(Comment comment) {

		commentRepository.save(comment);

	}

	@Transactional(readOnly = true)
	public List<CommentDto> getCommentList(Long id) {

		List<CommentDto> commentDtoList = commentRepository.findAllByOrderByBoardIdDesc(id);


		return commentDtoList;
	}

	@Transactional(readOnly = true)
	public Page<Comment> getCommentListPage(Long boardId, Pageable pageable) {
		
		return commentRepository.getCommentPage(boardId, pageable);
	}

	// 댓글 삭제
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}
	
	// 조회
	public Comment findById(Long id) {
		Optional<Comment> optionalComment = commentRepository.findById(id);
		if (optionalComment.isPresent()) {
			return optionalComment.get();
		}
		throw new NoSuchElementException("해당 정보를 찾을 수 없습니다.");
	}
	
	
	// 업데이트
	public void updateComment(Long id, String content) {
		commentRepository.updateComment(id, content);
	}

	

}
