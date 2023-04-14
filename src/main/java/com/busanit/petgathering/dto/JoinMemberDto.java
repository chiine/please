package com.busanit.petgathering.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinMemberDto {
	private String id;
	
	private String password;
	
	private String name;
	
	private String address;
	
	private String phone;
	
	private String email;
	
	private LocalDate created_by;
	
	private Date modified_by;
	
}
