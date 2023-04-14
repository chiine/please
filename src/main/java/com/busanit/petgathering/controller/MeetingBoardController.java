package com.busanit.petgathering.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.busanit.petgathering.dto.MeetingBoardDto;
import com.busanit.petgathering.dto.MeetingMemberDto;
import com.busanit.petgathering.dto.MemberSearchDto;
import com.busanit.petgathering.entity.MeetingBoard;
import com.busanit.petgathering.entity.MeetingMember;
import com.busanit.petgathering.repository.MeetingBoardRepository;
import com.busanit.petgathering.repository.MeetingMemberRepository;
import com.busanit.petgathering.service.MeetingBoardService;
import com.busanit.petgathering.service.MeetingMemberService;
import com.busanit.petgathering.util.CommonUtil;

@RequestMapping("/meeting")
@Controller
public class MeetingBoardController {
	
	
	@Autowired
	MeetingBoardService meetingBoardService;
	

	@Autowired
	MeetingMemberService meetingMemberService;
	
	@Autowired
	MeetingBoardRepository meetingBoardRepository;
	
	@Autowired
	MeetingMemberRepository meetingMemberRepository;
	
	
	@GetMapping("/new")
	public String newMeeting(Model model) {
		
		model.addAttribute("meetingBoardDto", new MeetingBoardDto());
		
		
		return "meetingBoard/meetingBoardForm";
		
	}
	
	@PostMapping("/new")
	public String createMeeting(@Valid MeetingBoardDto meetingBoardDto, BindingResult bindingResult, Model model, Principal principal) throws Exception {
		if(bindingResult.hasErrors()) {
			return "meetingBoard/meetingBoardForm";
		}
		try {
			Long meetingBoardId;
			meetingBoardId = meetingBoardService.saveMeeting(meetingBoardDto, CommonUtil.getId(principal.getName()));
	          model.addAttribute("message", "게시글 등록이 완료되었습니다.");
			  model.addAttribute("searchUrl", "/meeting/list");
	        } catch (IllegalStateException e){
	            model.addAttribute("errorMessage", e.getMessage());
	            return "member/memberForm";
	        }
		
		 return "meetingBoard/message";
	}
	
	@GetMapping({"/list", "/list/{page}"})//게시글 리스트 폼
	public String meetingList(MemberSearchDto memberSearchDto, @PathVariable("page") Optional<Integer> page,Model model){
		
		Pageable pageable = PageRequest.of(page.isPresent()?page.get() : 0, 10);
		Page<MeetingBoard> meetingBoards = 
				meetingBoardService.getMeetingBoardPage(memberSearchDto, pageable);
		model.addAttribute("list", meetingBoards);
		model.addAttribute("memberSearchDto", memberSearchDto);
		model.addAttribute("maxPage", 5);
		
			
		return "meetingBoard/meetingBoardList";
	}
	
	
	@GetMapping("/view/{meetingId}")
	public String meetingView(Model model,@PathVariable("meetingId") Long meetingId) {
		
		MeetingBoardDto meetingBoardDto = meetingBoardService.getMeetingList(meetingId);
		model.addAttribute("meetingBoard", meetingBoardDto);
		
		List<MeetingMember> meetingList = meetingMemberRepository.findByMeetingBoardId(meetingId);
		model.addAttribute("meetingMember", meetingList);
		
		
		return "meetingBoard/meetingBoardView";
			
	}
	
	@PostMapping("/{meetingId}/gomeeting")
	public String goMeeting(@Valid MeetingMemberDto meetingMemberDto, Model model,@PathVariable("meetingId") Long meetingId) {
			
			MeetingMember meetingMember = MeetingMember.goMeeting(meetingMemberDto);
			MeetingMember findMember = meetingMemberRepository.findByGoMemberAndMeetingBoardId(meetingMember.getGoMember(), meetingId);
			
			if(findMember == null) {
				meetingMemberService.createMeetingMember(meetingMember);
				model.addAttribute("message","신청완료되었습니다");
				model.addAttribute("searchUrl","/meeting/view/" + meetingId);	
			}else {
				
				model.addAttribute("message","이미 신청된 회원입니다");
				model.addAttribute("searchUrl","/meeting/view/" + meetingId);
			}
			
			
		return "meetingBoard/message";
	}
	
	@GetMapping("/modify/{meetingId}")
	public String MeetingBoardModify(@PathVariable("meetingId") Long meetingId, Model model) {
		
		MeetingBoardDto meetingBoardDto = meetingBoardService.boardView(meetingId);
		
		model.addAttribute("meetingBoardDto", meetingBoardDto);
		
		return "/meetingBoard/meetingBoardForm";
	}
	
	@PostMapping("/modify/{meetingId}")
	public String MeetingBoardUpdate(MeetingBoardDto meetingBoardDto,Model model,@PathVariable("meetingId") Long meetingId) {
		
		meetingBoardService.updateMeetingBoard(meetingBoardDto);
		model.addAttribute("message","수정되었습니다");
		model.addAttribute("searchUrl", "/meeting/view/"+ meetingId);
		
		return "/meetingBoard/message";
	}
	

	@PostMapping("/delete/{meetingId}")
	public String deleteMeeting(@PathVariable("meetingId") Long meetingId, Model model) {
		
		meetingBoardService.deleteMeeting(meetingId);
		model.addAttribute("message", "게시글이 삭제되었습니다");
		model.addAttribute("searchUrl", "/meeting/list");
		
		return "meetingBoard/message";
		
	}
	
	
	@PostMapping("/{meetingId}/{meetingMemberId}/allow")
	public String meetingAllow(Model model,@PathVariable("meetingId") Long meetingId,@PathVariable("meetingMemberId") Long meetingMemberId) {
			
		  try {
			  meetingMemberService.updateMeetingMember(meetingMemberId);
			  
	          model.addAttribute("message","수락되었습니다");
	          model.addAttribute("searchUrl","/meeting/view/"+meetingId);
	          
		  } catch (Exception e){
	            return "meetingBoard/meetingBoardView";
	        }

	
		return "meetingBoard/message";
	}

	
	
	@PostMapping("/{meetingId}/finish")
	public String meetingFinish(MeetingBoard meetingBoard, Model model,@PathVariable("meetingId") Long meetingId) {
		try {
			meetingBoard = meetingBoardService.finishMeetingMember(meetingId);
			model.addAttribute("message" , "모집이 종료되었습니다");
			model.addAttribute("searchUrl", "/meeting/view/" + meetingId );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "meetingBoard/message";
	}
	
    //내가 쓴 모임 게시글 조회
		@GetMapping("/mymeeting")
		public String myBoardPost(Principal principal, Model model, 
									@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
									Pageable pageable) {
			
			Page<MeetingBoard> list = meetingBoardService.myMBoardList(CommonUtil.getId(principal.getName()),pageable);
			int nowPage = list.getPageable().getPageNumber() + 1;
			int startPage = Math.max(nowPage - 4, 1);
			int endPage = Math.min(nowPage + 5, list.getTotalPages());
			
			
			Page<MeetingBoard> myMeetingBoardList = meetingBoardService.myMBoardList(CommonUtil.getId(principal.getName()), pageable);
			model.addAttribute("mymeeting", myMeetingBoardList);
			model.addAttribute("list", list);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			
			return "member/myMeetingPost";
		}
		
	 
	    //마이페이지 내가 쓴 게시물 삭제
	    @GetMapping(value = "/mymeeting/delete/{meetingId}")
	    public String deleteMyMeetingPost(@PathVariable("meetingId") Long meetingId, Model model){
	    	meetingBoardService.deleteMeeting(meetingId);
	        return "redirect:/meeting/mymeeting";
	    }
	
	    @PostMapping("/{meetingId}/{meetingMemberId}/delete")
		public String meetingDelete(Model model,@PathVariable("meetingId") Long meetingId,@PathVariable("meetingMemberId") Long meetingMemberId) {
				
				  meetingMemberService.deleteMeetingMember(meetingMemberId);
		          model.addAttribute("message","신청 취소되었습니다");
		          model.addAttribute("searchUrl","/meeting/view/"+meetingId);
		          
			return "meetingBoard/message";
		}
		
}
