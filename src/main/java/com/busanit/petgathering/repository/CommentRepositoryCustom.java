package com.busanit.petgathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busanit.petgathering.entity.Comment;

public interface CommentRepositoryCustom {
    Page<Comment> getCommentPage(Long boardId, Pageable pageable);
}
