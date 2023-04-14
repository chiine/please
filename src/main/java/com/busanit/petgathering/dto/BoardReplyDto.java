package com.busanit.petgathering.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardReplyDto {
	
	private Long id;

    @NotBlank(message = "댓글 내용을 입력하세요.")
	private String rContent;
	
	private LocalDateTime rRegDate;
		
}

