package com.busanit.petgathering.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.busanit.petgathering.dto.MemberSearchDto;
import com.busanit.petgathering.entity.MeetingBoard;
import com.busanit.petgathering.entity.QMeetingBoard;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class MeetingMemberRepositoryCustomImpl implements MeetingMemberRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public MeetingMemberRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression regDateAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }
			
		return QMeetingBoard.meetingBoard.meetingUploadDate.after(dateTime);
		
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		
		if(StringUtils.equals("title", searchBy)) {
			return QMeetingBoard.meetingBoard.title.like("%" + searchQuery + "%");
		}else if(StringUtils.equals("meetingAdr", searchBy)) {
			return QMeetingBoard.meetingBoard.meetingAdr.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	
	
	@Override
	public Page<MeetingBoard> getMeetingBoardPage(MemberSearchDto memberSearchDto, Pageable pageable) {
		QueryResults<MeetingBoard> results = queryFactory
				.selectFrom(QMeetingBoard.meetingBoard)
				.where(searchByLike(memberSearchDto.getSearchBy(),
		memberSearchDto.getSearchQuery()))
				.orderBy(QMeetingBoard.meetingBoard.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
				
		List<MeetingBoard> content = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	

}
