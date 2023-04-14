package com.busanit.petgathering.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busanit.petgathering.entity.MeetingMember;
import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.repository.MeetingBoardRepository;
import com.busanit.petgathering.repository.MeetingMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingMemberService {
	
	@Autowired
	private final MeetingMemberRepository meetingMemberRepository;
	
	
		public MeetingMember createMeetingMember(MeetingMember meetingMember) {

			return meetingMemberRepository.save(meetingMember);
	}
		
	public MeetingMember updateMeetingMember(Long meetingMemberId) throws Exception{
		MeetingMember meetingMember = meetingMemberRepository.findById(meetingMemberId)
				.orElseThrow(EntityNotFoundException::new);
		
		
		meetingMember.meetingAllow("allow");
		
		return meetingMemberRepository.save(meetingMember);
	}
	
	public void deleteMeetingMember(Long meetingMemberId) {
		meetingMemberRepository.deleteById(meetingMemberId);
	}
	
	
}