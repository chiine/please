package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.MeetingMember;

public interface MeetingMemberRepository  extends JpaRepository<MeetingMember, Long> {

	List<MeetingMember> findByMeetingBoardId(Long meetingId);
	
	MeetingMember findByGoMemberAndMeetingBoardId(String goMember, Long meetingId);
}
