package com.busanit.petgathering.dto;

import org.modelmapper.ModelMapper;

import com.busanit.petgathering.entity.DogImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DogImgDto {
	
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repImgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();	
	
	public static DogImgDto of(DogImg dogImg) {
		return modelMapper.map(dogImg, DogImgDto.class);
	}
}
