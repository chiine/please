package com.busanit.petgathering.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.busanit.petgathering.entity.Comment;
import com.busanit.petgathering.entity.QComment;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(EntityManager em){
        this.queryFactory= new JPAQueryFactory(em);
    }




	@Override
	public Page<Comment> getCommentPage(Long boardId, Pageable pageable) {
		 QueryResults<Comment> results = queryFactory
	                .selectFrom(QComment.comment)
	                .where(QComment.comment.id.eq(boardId))
	                .orderBy(QComment.comment.regTime.desc())
	                .offset(pageable.getOffset())
	                .limit(pageable.getPageSize())
	                .fetchResults();

	        List<Comment> content = results.getResults();
	        for(int i=0; i<content.size(); i++) {
	        	System.out.println(content.get(i).getContent());
	        }
	        
	        
	        long total = results.getTotal();
	        System.out.println(total);
	        return new PageImpl<>(content,pageable,total);
	}
}
