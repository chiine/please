package com.busanit.petgathering.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.busanit.petgathering.entity.Dog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DogFormDto {
	
	private Long id;
	
	
	private String myType;

	@NotBlank(message="이름을 입력하세요")
	private String name;
	
	@NotNull(message="나이를 입력해 주세요")
	private Integer age;
	
	@NotBlank(message="품종을 입력해 주세요")
	private String dogType;
	
	
	private String imgUrl;
	
	
	private String gender;
	
	@Length(min=0, max=1000)
	private String content;
	
	
	private List<DogImgDto> dogImgDtoList= new ArrayList<>();
	
	private List<Long> dogImgIds= new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Dog createDog() {
		return modelMapper.map(this,Dog.class);
	}
	
	public static DogFormDto of(Dog dog) {
		return modelMapper.map(dog,DogFormDto.class);
	}
}
