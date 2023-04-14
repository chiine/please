package com.busanit.petgathering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findById(String id);
	
	Member findByNum(Long num);

     List<Member> findAll();
     
     Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}
