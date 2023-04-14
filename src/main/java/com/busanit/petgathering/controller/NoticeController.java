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
import com.busanit.petgathering.dto.NoticeDto;
import com.busanit.petgathering.entity.Comment;
import com.busanit.petgathering.entity.Notice;
import com.busanit.petgathering.service.CommentService;
import com.busanit.petgathering.service.NoticeService;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/notice/write")
	public String noticeWriteForm(Model model) {
		model.addAttribute("NoticeDto", new NoticeDto());
		return "/notice/noticewrite";
	}
	
	@PostMapping("/notice/writePro")
	public String noticeWritePro(@Valid NoticeDto noticeDto, Model model, MultipartFile file, BindingResult result) throws Exception {
		
		// 유효성검사 결렬시
		if(result.hasErrors()) {
			return "/notice/noticewrite";
		}
		Notice notice = Notice.createNotice(noticeDto);
		
		noticeService.write(notice, file);
		model.addAttribute("message", "글 작성이 완료되었습니다.");
		model.addAttribute("searchUrl", "/notice/list");
		
		return "/notice/message";
	}
	
	@GetMapping("/notice/list")
	public String noticeListForm(Model model, 
								@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
								Pageable pageable) {
		
		Page<Notice> list = noticeService.noticeList(pageable);
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		
		
		model.addAttribute("noticeList", noticeService.noticeList(pageable));
		model.addAttribute("list", list);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "/notice/noticeList";
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
	
	@PostMapping("/notice/delete")
	public String deletePost(@RequestParam final Long id) {
		noticeService.noticeDelete(id);
		return "redirect:/notice/list";
	}
	
	@GetMapping("/notice/modify/{id}")
	public String noticeModify(@PathVariable("id") Long id, Model model) {
		
		Notice notice = noticeService.noticeView(id);
		model.addAttribute("notice", notice);
		// model.addAttribute("filename", "/files/" + board.getFilename());
		model.addAttribute("filename", "/images/noticeImg/" + notice.getFilename());
		//model.addAttribute("filename", "/images/item/" + board.getFilename());
		
		return "/notice/noticeModify";
	}
	
	@PostMapping("/notice/update/{id}")
	public String noticeUpdate(@PathVariable("id") Long id, Model model, Notice notice, MultipartFile file) throws Exception {
		Notice noticeTemp = noticeService.noticeView(id);
		
		noticeTemp.setNTitle(notice.getNTitle());
		noticeTemp.setNContent(notice.getNContent());
		noticeTemp.setFilename(notice.getFilename());
		
		noticeService.write(noticeTemp, file);
		
		model.addAttribute("message", "글 작성이 완료되었습니다.");
		model.addAttribute("searchUrl", "/notice/view/" + id);
		
		return "/notice/message";
	}
	
	@GetMapping(value = {"/notice/view/{noticeId}", "/notice/view/{noticeId}/{page}"})
	public String noticeView(Model model, @PathVariable("noticeId") Long noticeId, @PathVariable("page") Optional<Integer> page) {
		NoticeDto noticeDto = noticeService.getNoticeDtl(noticeId);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		 
		Page<Comment> comments = commentService.getCommentListPage(noticeId, pageable);
		System.out.println("------------------------------");
		System.out.println("aaaaaaaaaaaaaaaaaaaaaa "+noticeId);
		System.out.println("------------------------------");
		//List<CommentDto> commentDtoList = commentService.getCommentList(itemId);
		
		// 여기에다 댓글 추가할꺼임
		
		
		// List<CommentDto> commentDtoList = commentService.getCommentList(itemId);
		
		//if(commentDtoList == null) {
			
		//}else {
			
		//}
		
		
		model.addAttribute("notice", noticeDto);
		model.addAttribute("comment",comments);
		model.addAttribute("maxPage", 100);
		
		return "notice/noticeView";
	}
	

}










