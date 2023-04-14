package com.busanit.petgathering.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.busanit.petgathering.dto.MeetingMemberDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="meeting_member")
@ToString
public class MeetingMember {
	
	@Id
	@Column(name="meeting_member")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String goMember;
	
	private String meeting;
	
	private String allow;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="meeting_id")
	private MeetingBoard meetingBoard;
	
	
	
	
	public static MeetingMember goMeeting(MeetingMemberDto meetingMemberDto) {
		MeetingMember meetingMember = new MeetingMember();
		
		meetingMember.setGoMember(meetingMemberDto.getGoMember());
		meetingMember.setMeeting(meetingMemberDto.getMeeting());
		meetingMember.setMeetingBoard(meetingMemberDto.getMeetingBoard());
		
		return meetingMember;
		
	}
	
	public void meetingAllow(String allow) {
		
		this.allow = allow;
		
	}
	
	
}
