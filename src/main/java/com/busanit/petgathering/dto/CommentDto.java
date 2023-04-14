package com.busanit.petgathering.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {
	
	private int idx;
	
	private Long id;
	
	private String content;


//	private String writer;
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;

	
	private List<CommentDto> commentDtoList;
	
	
	// public CommentDto(Long id, String content, String writer) {
	public CommentDto(Long id, String content) {
		this.id = id;
		this.content = content;
//		this.writer = writer;
		
//			this.deliveryStatus = deliveryStatus;
	}
}