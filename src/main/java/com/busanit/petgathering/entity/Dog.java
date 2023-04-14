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

import com.busanit.petgathering.dto.DogFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="gathering_board")
@Getter
@Setter
@ToString
public class Dog {

	@Id
	@Column(name="dog_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String myType;
	
	private String name;
	
	private int age;
	
	private String dogType;
	
	private String gender;
	
	private String imgUrl;
	
	private String content;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_num")
	private Member member;
	
	
	public static Dog createDog(DogFormDto dogFormDto, Member member) {
		
		Dog dog = new Dog();
		dog.setMember(member);
		dog.setMyType(dogFormDto.getMyType());
		dog.setName(dogFormDto.getName());
		dog.setAge(dogFormDto.getAge());
		dog.setDogType(dogFormDto.getDogType());
		dog.setGender(dogFormDto.getGender());
		dog.setContent(dogFormDto.getContent());
		
		
		
		return dog;
	}
	
	public void updateDog(DogFormDto dogFormDto) {
		
		this.myType = dogFormDto.getMyType();
		this.name = dogFormDto.getName();
		this.age = dogFormDto.getAge();
		this.dogType =dogFormDto.getDogType();
		this.gender = dogFormDto.getGender();
		this.content = dogFormDto.getContent();
				
	}
	
}
