package com.busanit.petgathering.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.busanit.petgathering.entity.Notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
	
	private Long id;
	
	private int nNum;
	
	// @NotNull - null 허용x
	// @NotEmpty - null, "" 허용x
	// @NotBlank - null, "", " " 허용x
	@NotEmpty(message = "제목은 필수입니다.")
	private String nTitle;
	
	private String nContent;
	
	private String createdBy;
	
	private String filename;
	
	private String filepath;
	
	private LocalDateTime nRegDate;
	
	private LocalDateTime nUpdateDate;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	 public static NoticeDto of(Notice notice){
	        return modelMapper.map(notice, NoticeDto.class);
	    }
	 
	
}

