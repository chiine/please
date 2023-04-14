package com.busanit.petgathering.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.repository.MemberRepository;
import com.busanit.petgathering.repository.UserRepository;


@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findById(username);
		if(member == null) {
			return null;
		}else {
			return new PrincipalDetails(member);
		}
		
	}

}
