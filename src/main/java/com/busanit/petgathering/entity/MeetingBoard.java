package com.busanit.petgathering.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.busanit.petgathering.dto.MeetingBoardDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="meeting_board")
@ToString
public class MeetingBoard {

	@Id
	@Column(name="meeting_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String meetingAdr;
	
	private String meetingComment;
	
	@CreationTimestamp
	private LocalDateTime meetingUploadDate;
	
	private String meetingWriter;
	
	private int meetingCount;
	
	private String finish;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	@OneToMany(mappedBy="meetingBoard",orphanRemoval = true)
	private List<MeetingMember> meetingMember;
	
	public static MeetingBoard createMeeting(MeetingBoardDto meetingBoardDto, Member member){
		MeetingBoard meetingBoard = new MeetingBoard();
		
		meetingBoard.setMember(member);
		meetingBoard.setTitle(meetingBoardDto.getTitle());
		meetingBoard.setMeetingAdr(meetingBoardDto.getMeetingAdr());
		meetingBoard.setMeetingWriter(meetingBoardDto.getMeetingWriter());
		meetingBoard.setMeetingComment(meetingBoardDto.getMeetingComment());
		meetingBoard.setMeetingCount(meetingBoardDto.getMeetingCount());
		meetingBoard.setMeetingUploadDate(meetingBoardDto.getMeetingUploadDate());
		
		return meetingBoard;
	}
	
	public void updateFinishBoard(String finish) {
		this.finish = finish;
	}
	
	public void updateMeetingBoard(MeetingBoardDto meetingBoardDto) {
		this.title = meetingBoardDto.getTitle();
		this.meetingAdr = meetingBoardDto.getMeetingAdr();
		this.meetingComment = meetingBoardDto.getMeetingComment();
		this.meetingCount = meetingBoardDto.getMeetingCount();
	}
	


}
