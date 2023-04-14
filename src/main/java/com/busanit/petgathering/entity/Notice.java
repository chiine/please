package com.busanit.petgathering.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.busanit.petgathering.dto.NoticeDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="notice")
@Getter
@Setter
@ToString
@Data
public class Notice extends BaseEntity {
	
	@Id
	@Column(name="notice_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int nNum;
	
	private String nTitle;
	
	private String nContent;
	
	private String filename;  // 파일 이름
	
	private String filepath;  // 파일 경로
	
	@CreationTimestamp
	private LocalDateTime nRegDate;
	
	@UpdateTimestamp
	private LocalDateTime nUpdateDate;
	
	
	public static Notice createNotice(NoticeDto noticeDto) {
		Notice notice = new Notice();
		notice.setNNum(noticeDto.getNNum());
		notice.setNTitle(noticeDto.getNTitle());
		notice.setNContent(noticeDto.getNContent());
		notice.setFilename(noticeDto.getFilename());
		notice.setFilepath(noticeDto.getFilepath());
		notice.setNRegDate(noticeDto.getNRegDate());
		notice.setNUpdateDate(noticeDto.getNUpdateDate());
		
		return notice;
	}
	
}
