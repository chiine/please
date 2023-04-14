package com.busanit.petgathering.entity;


import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;



@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
public class Comment extends BaseEntity {

	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idx;

	@Column(name = "board_id")
	private Long id;

	private String content;
//	private String writer;


	private LocalDateTime regTime; // 등록시간
	

	private LocalDateTime updateTime; // 수정 시간
	
}
