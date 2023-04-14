package com.busanit.petgathering.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.busanit.petgathering.dto.BoardDto;
import com.busanit.petgathering.entity.Board;
import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.entity.Qna;
import com.busanit.petgathering.repository.BoardRepository;
import com.busanit.petgathering.repository.MemberRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
//	@Value("${uploadPath}")
	@Value("${upload.path}")
	private String uploadPath;
	
	
	public void write(Board board, MultipartFile file) throws Exception {
		
		
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
			board.setFilename(fileName);  // 저장된 파일 이름
			board.setFilepath("/images/board/" + fileName); // 저장된 파일 경로
		}
		

		// 파일 저장
		boardRepository.save(board);
	}
	
	 public Long saveBoard(BoardDto boardDto, String id,  MultipartFile file) throws Exception {
		 
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
				boardDto.setFilename(fileName);  // 저장된 파일 이름
				boardDto.setFilepath("/images/board/" + fileName); // 저장된 파일 경로
			}
	        Member member = memberRepository.findById(id);
	        Board board = Board.createBoard(boardDto, member);

	        boardRepository.save(board);

	        return board.getId();
	    }
	
	
	// 특정 게시글 불러오기
	public Board boardView(Long id) {
		return boardRepository.findById(id).get();
	}
	
	// 게시글 리스트 처리
	public Page<Board> boardList(Pageable pageable) {
		//findAll : 테스트보드라는 클래스가 담긴 list를 반환하는 것을 확인할 수 있다.
		return boardRepository.findAll(pageable);
	}
//	public List<Board> boardList() {
//		return boardRepository.findAllByOrderByIdDesc();
//	}
	
	// 특정 게시글 삭제
	public void boardDelete(Long id) {
		boardRepository.deleteById(id);
	}
	
	// 정보 조회
	@Transactional(readOnly = true)
	public BoardDto getBoardDtl(Long boardId){
		Board board = boardRepository.findById(boardId)
				.orElseThrow(EntityNotFoundException::new);
		BoardDto boardDto = BoardDto.of(board);
		return boardDto;
	}

	//마이페이지 내가 쓴 게시글 리스트
	 public Page<Board> myBoardList(String id,Pageable pageable){

	        Member member = memberRepository.findById(id);
	        Page<Board>myBoardList = boardRepository.findByMemberNum(member.getNum(),pageable);

	        return myBoardList;
	    }
}
