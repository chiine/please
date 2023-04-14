package com.busanit.petgathering.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.busanit.petgathering.entity.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	
	private Long id;
	
	private int bNum;
	
	// @NotNull - null 허용x
	// @NotEmpty - null, "" 허용x
	// @NotBlank - null, "", " " 허용x
	@NotEmpty(message = "제목은 필수입니다.")
	private String bTitle;
	
	private String bContent;
	
	private String createdBy;
	
	private String filename;
	
	private String filepath;
	
	private LocalDateTime bRegDate;
	
	private LocalDateTime bUpdateDate;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	 public static BoardDto of(Board board){
	        return modelMapper.map(board, BoardDto.class);
	    }
	 
	 private List<Board> boardList = new ArrayList<>();
	
}

