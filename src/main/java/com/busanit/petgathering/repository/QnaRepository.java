package com.busanit.petgathering.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.busanit.petgathering.entity.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    List<Qna> findByMemberNum(Long memberNum);

    Page<Qna> findAll(Pageable pageable);
}