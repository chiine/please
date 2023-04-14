package com.busanit.petgathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busanit.petgathering.dto.MemberSearchDto;
import com.busanit.petgathering.entity.MeetingBoard;

public interface MeetingMemberRepositoryCustom {

	Page<MeetingBoard> getMeetingBoardPage(MemberSearchDto memberSearchDto, Pageable pageable);
	

}
