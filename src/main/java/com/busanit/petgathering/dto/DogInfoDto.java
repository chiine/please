package com.busanit.petgathering.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DogInfoDto {

    private Long id;

    private String dogNm;

    private String dogDetail;
	
}
