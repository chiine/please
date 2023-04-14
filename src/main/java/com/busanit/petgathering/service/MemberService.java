package com.busanit.petgathering.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busanit.petgathering.dto.MemberFormDto;
import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findById(member.getId());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Transactional(readOnly = true)
    public MemberFormDto getMemberDtl(String memberId){
        Member member = memberRepository.findById(memberId);
        List<MemberFormDto> memberFormDtoList = new ArrayList<>();

        MemberFormDto memberFormDto = MemberFormDto.of(member);
        memberFormDtoList.add(memberFormDto);

        memberFormDto.setMemberFormDtoList(memberFormDtoList);

        return memberFormDto;
    }

    @Transactional(readOnly = true)
    public MemberFormDto getMypageList(String id){
        Member member = memberRepository.findById(id);
        MemberFormDto memberFormDto = MemberFormDto.of(member);

        return memberFormDto;
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

    public long updateMember(MemberFormDto memberFormDto) throws Exception{
        Member member = memberRepository.findById(memberFormDto.getNum()).orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberFormDto);

        return member.getNum();
    }
    
    

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id);

        if(member == null) {
            throw new UsernameNotFoundException(id);
        }

        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}