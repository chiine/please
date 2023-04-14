package com.busanit.petgathering.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.busanit.petgathering.dto.BoardDto;
import com.busanit.petgathering.entity.Board;
import com.busanit.petgathering.entity.Comment;
import com.busanit.petgathering.service.BoardService;
import com.busanit.petgathering.service.CommentService;
import com.busanit.petgathering.util.CommonUtil;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	
	@GetMapping("/board/write")
	public String boardWriteForm(Model model) {
		model.addAttribute("BoardDto", new BoardDto());
		return "/board/boardwrite";
	}
	
	@PostMapping("/board/writePro")
	public String boardWritePro(@Valid BoardDto boardDto, Model model, MultipartFile file, BindingResult result, Principal principal) throws Exception {
		
		// 유효성검사 결렬시
		if(result.hasErrors()) {
			return "/board/boardwrite";
		}
		
		Long boardId;
		boardId = boardService.saveBoard(boardDto, CommonUtil.getId(principal.getName()), file);

		model.addAttribute("message", "글 작성이 완료되었습니다.");
		model.addAttribute("searchUrl", "/board/list");
		
		return "/board/message";
	}
	
	
	@GetMapping("/board/list")
	public String boardListForm(Model model, 
								@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
								Pageable pageable) {
		
		Page<Board> list = boardService.boardList(pageable);
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		
		
		model.addAttribute("boardList", boardService.boardList(pageable));
		model.addAttribute("list", list);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "/board/boardList";
	}
	
//	@GetMapping("/board/view/{boardId}")
//	public String boardView(Model model, @PathVariable("boardId") String boardId,
//			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
//	Pageable pageable) {
//		
//		Page<Comment> comments = commentService.getCommentListPage(Long.parseLong(boardId), pageable);
//		
//		model.addAttribute("board", boardService.boardView(Long.parseLong(boardId)));
//		model.addAttribute("comment",comments);
////		model.addAttribute("dtoList", commentService.getListOfBoard(id, pageable));
//		
//		return "/board/boardView";
//	}
	
	@PostMapping("/board/delete")
	public String deletePost(@RequestParam final Long id) {
		boardService.boardDelete(id);
		return "redirect:/board/list";
	}
	
	@GetMapping("/board/modify/{id}")
	public String boardModify(@PathVariable("id") Long id, Model model) {
		
		Board board = boardService.boardView(id);
		
		model.addAttribute("board", board);
		
		// model.addAttribute("filename", "/files/" + board.getFilename());
		model.addAttribute("filename", "/images/boardImg/" + board.getFilename());
		//model.addAttribute("filename", "/images/item/" + board.getFilename());
		
		return "/board/boardModify";
	}
	
	@PostMapping("/board/update/{id}")
	public String boardUpdate(@PathVariable("id") Long id, Board board, MultipartFile file) throws Exception {
		Board boardTemp = boardService.boardView(id);
		
		boardTemp.setBTitle(board.getBTitle());
		boardTemp.setBContent(board.getBContent());
		boardTemp.setFilename(board.getFilename());
		
		boardService.write(boardTemp, file);
		
		return "redirect:/board/view/" + id;
	}
	
//	Long BoardId1;
	
	@PostMapping("/board/comment/write")
	public ResponseEntity<String> insertComment(@RequestParam("content") String content,@RequestParam("boardId") Long BoardId, 
			Principal principal, Model model) throws Exception{
		System.out.println("---------------------------");
		System.out.println(content);
		System.out.println(BoardId);
		
		System.out.println("---------------------------");
		
		Comment comment = new Comment();
		comment.setContent(content);
//		comment.setWriter(CommonUtil.getId(principal.getName()));
		comment.setId(BoardId);
		
		if(principal != null ) {
			commentService.CommentInsert(comment);
			
			// status 200 로그인 상태
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			// status 401 비로그인 상태
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
//		BoardId1 = BoardId;
	}
	
	 @GetMapping(value = {"/board/view/{boardId}", "/board/view/{boardId}/{page}"})
		public String boardView(Model model, @PathVariable("boardId") Long boardId, @PathVariable("page") Optional<Integer> page) {
			BoardDto boardDto = boardService.getBoardDtl(boardId);
			
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
			 
			Page<Comment> comments = commentService.getCommentListPage(boardId, pageable);
			System.out.println("------------------------------");
			System.out.println("aaaaaaaaaaaaaaaaaaaaaa "+boardId);
			System.out.println("------------------------------");
			//List<CommentDto> commentDtoList = commentService.getCommentList(itemId);
			
			// 여기에다 댓글 추가할꺼임
			
			
			// List<CommentDto> commentDtoList = commentService.getCommentList(itemId);
			
			//if(commentDtoList == null) {
				
			//}else {
				
			//}
			
			
			model.addAttribute("board", boardDto);
			model.addAttribute("comment",comments);
			model.addAttribute("maxPage", 100);
			
			return "board/boardView";
		}
	 
	    //내가 쓴 수다 게시글 조회
		@GetMapping("/board/myboard")
		public String myBoardPost(Principal principal, Model model, 
									@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
									Pageable pageable) {
			
			Page<Board> list = boardService.myBoardList(CommonUtil.getId(principal.getName()),pageable);
			int nowPage = list.getPageable().getPageNumber() + 1;
			System.out.println(nowPage);
			int startPage = Math.max(nowPage - 4, 1);
			System.out.println(startPage);
			int endPage = Math.min(nowPage + 5, list.getTotalPages());
			System.out.println(endPage);
			
			
			Page<Board> myBoardList = boardService.myBoardList(CommonUtil.getId(principal.getName()), pageable);
			model.addAttribute("myboard", myBoardList);
			model.addAttribute("list", list);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			
			return "member/myBoardPost";
		}
		
	 
	    //마이페이지 내가 쓴 게시물 삭제
	    @GetMapping(value = "/board/delete/{id}")
	    public String deleteMyPost(@PathVariable("id") Long id){
	        boardService.boardDelete(id);
	        return "redirect:/board/myboard";
	    }
	    
}










