package com.busanit.petgathering.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.busanit.petgathering.entity.MeetingBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingBoardDto {

	private Long id;
	
	private String meetingAdr;
	
	@NotBlank(message="제목을 입력해주세요")
	private String title;

	@NotBlank(message="내용을 입력해주세요")
	private String meetingComment;
	
	private LocalDateTime meetingUploadDate;
	
	private String meetingWriter;
	
	private String finish;
	
	@NotNull(message="모집인원을 입력해주세요")
	@Min(value = 1, message = "최소 모집인원은 1명입니다.")
	private Integer meetingCount;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MeetingBoardDto of(MeetingBoard meetingBoard){
        return modelMapper.map(meetingBoard, MeetingBoardDto.class);
    }
	
	 private List<MeetingBoard> meetingBoardList = new ArrayList<>();
	
}
