package com.busanit.petgathering.service;

import java.io.File;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.busanit.petgathering.dto.NoticeDto;
import com.busanit.petgathering.entity.Notice;
import com.busanit.petgathering.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
//	@Value("${uploadPath}")
	@Value("${upload.path}")
	private String uploadPath;
	
	
	public void write(Notice notice, MultipartFile file) throws Exception {
		
		
		if(file != null && file.getSize() > 0) {
			// 저장 경로 지정
			// String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
			String projectPath = uploadPath;
			// 랜덤으로 파일 이름 만들어줌
			UUID uuid = UUID.randomUUID();
			// 랜덤식별자_원래파일이름 = 저장될 파일이름 지정
			String fileName = uuid + "_" + file.getOriginalFilename();
			// 파일을 넣을 껍데기 생성
			File saveFile = new File(projectPath, fileName);
			file.transferTo(saveFile);
			// DB에 값 넣기
			notice.setFilename(fileName);  // 저장된 파일 이름
			notice.setFilepath("/images/notice/" + fileName); // 저장된 파일 경로
		}
		

		// 파일 저장
		noticeRepository.save(notice);
	}
	
	
	// 특정 게시글 불러오기
	public Notice noticeView(Long id) {
		return noticeRepository.findById(id).get();
	}
	
	// 게시글 리스트 처리
	public Page<Notice> noticeList(Pageable pageable) {
		//findAll : 테스트보드라는 클래스가 담긴 list를 반환하는 것을 확인할 수 있다.
		return noticeRepository.findAll(pageable);
	}
//	public List<Board> boardList() {
//		return boardRepository.findAllByOrderByIdDesc();
//	}
	
	// 특정 게시글 삭제
	public void noticeDelete(Long id) {
		noticeRepository.deleteById(id);
	}
	
	// 정보 조회
	@Transactional(readOnly = true)
	public NoticeDto getNoticeDtl(Long noticeId){
		Notice notice = noticeRepository.findById(noticeId)
				.orElseThrow(EntityNotFoundException::new);
		NoticeDto noticeDto = NoticeDto.of(notice);
		
		return noticeDto;
	}

}
