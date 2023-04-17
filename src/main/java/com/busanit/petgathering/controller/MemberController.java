package com.busanit.petgathering.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.busanit.petgathering.dto.MemberFormDto;
import com.busanit.petgathering.entity.Board;
import com.busanit.petgathering.entity.MeetingBoard;
import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.service.BoardService;
import com.busanit.petgathering.service.MeetingBoardService;
import com.busanit.petgathering.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
	@Autowired
	private BoardService boardService;
	@Autowired
	private MeetingBoardService meetingBoardService;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    //마이페이지
    @GetMapping(value = "/mypage")
    public String myPage(Principal principal, Model model) {
        MemberFormDto memberFormDto = memberService.getMypageList(principal.getName());
        model.addAttribute("memberFormDto", memberFormDto);
        return "member/myPage";
    }

    @GetMapping(value = "/{memberId}")
    public String memberDtl(@PathVariable("memberId") String memberId, Model model){
        try{
            MemberFormDto memberFormDto = memberService.getMemberDtl(memberId);
            model.addAttribute("memberFormDto", memberFormDto);
            
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 회원입니다.");
            model.addAttribute("memberFormDto", new MemberFormDto());
        }
        return "member/adminForm";
    }

    @PostMapping(value = "/{memberId}")
    public String memberUpdate(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                               Model model){
    	 if(bindingResult.hasErrors()){
             return "member/adminForm";
         }
        try{
            memberService.updateMember(memberFormDto, passwordEncoder);
            
        }catch (Exception e){
            model.addAttribute("errorMessage","회원정보 수정 중 에러가 발생하였습니다.");
            return "member/adminForm";
        }
        model.addAttribute("message","회원정보 수정이 완료되었습니다.");
        return "redirect:/members/list";
    }    
    
    @GetMapping(value = "/user")
    public String userDtl(Principal principal, Model model){

        MemberFormDto memberFormDto = memberService.getMypageList(principal.getName());
        model.addAttribute("memberFormDto", memberFormDto);

        return "member/userForm";
    }
    
  //회원정보 수정
    @PostMapping(value = "/user/{memberId}")
    public String memberUserUpdate(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                                   Model model){
   	 if(bindingResult.hasErrors()){
         return "member/userForm";
     }
        try{
            memberService.updateMember(memberFormDto, passwordEncoder);
            model.addAttribute("message", "회원정보 수정이 완료되었습니다.");
        }catch (Exception e){
            model.addAttribute("errorMessage","회원정보 수정 중 에러가 발생하였습니다.");
            return "member/userForm";
        }

        return "member/myPage";
    }


    //관리자페이지 회원목록
    @GetMapping(value = "/list")
    public String memberList(Model model){
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("memberList",memberList);

        return "member/memberList";
    }

    @PostMapping(value = "/new")
    public String newMember(MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            model.addAttribute("searchUrl", "/main");
            
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    //회원탈퇴
    @GetMapping(value = "/delete/{memberNum}")
    public String deleteMember(@PathVariable("memberNum") Long memberNum){
        memberService.deleteMember(memberNum);
        return "redirect:/members/list";
    }
}