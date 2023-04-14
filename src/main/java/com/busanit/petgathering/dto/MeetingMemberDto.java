package com.busanit.petgathering.dto;

import com.busanit.petgathering.entity.MeetingBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingMemberDto {

	private Long id;
	
	private String goMember;
	
	private String meeting;
	
	private MeetingBoard meetingBoard;
	
	private String allow;
	
}
