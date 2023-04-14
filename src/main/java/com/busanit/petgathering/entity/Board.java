package com.busanit.petgathering.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.busanit.petgathering.dto.BoardDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
@Data
public class Board extends BaseEntity {
	
	@Id
	@Column(name="board_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int bNum;
	
	private String bTitle;
	
	private String bContent;
	
	private String filename;  // 파일 이름
	
	private String filepath;  // 파일 경로
	
	@CreationTimestamp
	private LocalDateTime bRegDate;
	
	@UpdateTimestamp
	private LocalDateTime bUpdateDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
		
	
	public static Board createBoard(BoardDto boardDto, Member member) {
		Board board = new Board();
		board.setMember(member);
		board.setBNum(boardDto.getBNum());
		board.setBTitle(boardDto.getBTitle());
		board.setBContent(boardDto.getBContent());
		board.setFilename(boardDto.getFilename());
		board.setFilepath(boardDto.getFilepath());
		board.setBRegDate(boardDto.getBRegDate());
		board.setBUpdateDate(boardDto.getBUpdateDate());
		
		
		return board;
	}
	
}
