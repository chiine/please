package com.busanit.petgathering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busanit.petgathering.dto.MeetingBoardDto;
import com.busanit.petgathering.dto.MemberSearchDto;
import com.busanit.petgathering.entity.MeetingBoard;
import com.busanit.petgathering.entity.MeetingMember;
import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.repository.MeetingBoardRepository;
import com.busanit.petgathering.repository.MemberRepository;

import lombok.RequiredArgsConstructor;



@Service
@Transactional
@RequiredArgsConstructor
public class MeetingBoardService {

	@Autowired
	private final MeetingBoardRepository meetingBoardRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	public MeetingBoard saveMeeting(MeetingBoard meetingBoard){
	 
		
		return	meetingBoardRepository.save(meetingBoard);
		
	}
	
 public Long saveMeeting(MeetingBoardDto meetingboardDto, String id) throws Exception {
		 
	        Member member = memberRepository.findById(id);
	        MeetingBoard meetingBoard = MeetingBoard.createMeeting(meetingboardDto, member);

	        meetingBoardRepository.save(meetingBoard);

	        return meetingBoard.getId();
	    }
	
	
	@Transactional(readOnly = true)
    public MeetingBoardDto getMeetingList(Long id){
		
        MeetingBoard meetingBoard = meetingBoardRepository.findById(id)
        						.orElseThrow(EntityNotFoundException::new);
        MeetingBoardDto meetingBoardDto = MeetingBoardDto.of(meetingBoard);

        return meetingBoardDto;
    }
	
	@Transactional(readOnly = true)
	public Page<MeetingBoard> getMeetingBoardPage(MemberSearchDto memberSearchDto, Pageable pageable){
		return meetingBoardRepository.getMeetingBoardPage(memberSearchDto, pageable);
	}
	

	public void deleteMeeting(Long meetingId) {
		meetingBoardRepository.deleteById(meetingId);
	}
	
	public MeetingBoard finishMeetingMember(Long meetingBoardId) throws Exception{
		MeetingBoard meetingBoard = meetingBoardRepository.findById(meetingBoardId)
				.orElseThrow(EntityNotFoundException::new);
		
		
		meetingBoard.updateFinishBoard("finish");
		
		return meetingBoardRepository.save(meetingBoard);
	}
	
	
	
	// 특정 게시글 불러오기
		public MeetingBoardDto boardView(Long meetingId) {
			MeetingBoard meetingBoard = meetingBoardRepository.findById(meetingId).orElseThrow(EntityNotFoundException::new);
			
			MeetingBoardDto meetingBoardDto = MeetingBoardDto.of(meetingBoard);
			
			return meetingBoardDto;
		}
	
	//마이페이지 내가 쓴 게시글 리스트
		 public Page<MeetingBoard> myMBoardList(String id,Pageable pageable){

		        Member member = memberRepository.findById(id);
		        Page<MeetingBoard>myMBoardList = meetingBoardRepository.findByMemberNum(member.getNum(),pageable);

		        return myMBoardList;
		    }
	
		 public Long updateMeetingBoard(MeetingBoardDto meetingBoardDto) {
				MeetingBoard meetingBoard = meetingBoardRepository.findById(meetingBoardDto.getId()).orElseThrow(EntityNotFoundException::new);
				
				meetingBoard.updateMeetingBoard(meetingBoardDto);
				
				return meetingBoard.getId();
				
			}
}
