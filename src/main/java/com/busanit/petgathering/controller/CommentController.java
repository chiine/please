package com.busanit.petgathering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.busanit.petgathering.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	
	// 댓글 삭제기능
	/*
	 * @GetMapping("/comment/delete/{id}") public String
	 * deleteMember(@PathVariable("id") Long id) {
	 * 
	 * commentService.deleteById(id);
	 * 
	 * return "redirect:/"; }
	 */
	
	
	
	@PostMapping("/comment/delete")
	public ResponseEntity<String> deleteMemberPost(@RequestParam("idx") Long id) {
		System.out.println("댓글 삭제입니다.");
		commentService.deleteById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	
	// 댓글 업데이트 기능
	@GetMapping("/comment/update/{id}/{content}/{boardId}")
	public String updateComment(@PathVariable("id") Long id, @PathVariable("content") String content,
			@PathVariable("boardId") Long boardId) {
		System.out.println("댓글 업데이트입니다.");
		
		// 변경된 회원 정보 저장
		commentService.updateComment(id, content);

//		return "redirect:/";
		return "redirect:/board/view/" + boardId;
	}

	

	
}
