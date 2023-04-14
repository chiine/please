package com.busanit.petgathering.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busanit.petgathering.dto.QnaDto;
import com.busanit.petgathering.entity.Member;
import com.busanit.petgathering.entity.Qna;
import com.busanit.petgathering.repository.MemberRepository;
import com.busanit.petgathering.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    @Autowired
    private final QnaRepository qnaRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public QnaDto getQnaDtl(Long id) {
        Qna qna = qnaRepository.findById(id).get();
        QnaDto qnaDto = QnaDto.of(qna);

        return qnaDto;
    }

    public Long saveQna(QnaDto qnaDto, String id) {
        Member member = memberRepository.findById(id);
        Qna qna = Qna.createQna(qnaDto, member);

        qnaRepository.save(qna);

        return qna.getId();
    }

    public long updateQna(QnaDto qnaDto) throws Exception{
        Qna qna = qnaRepository.findById(qnaDto.getId()).orElseThrow(EntityNotFoundException::new);
        qna.updateQna(qnaDto);

        return qna.getId();
    }

    public List<Qna> qnaList(String id){
        List<Qna> qnaList = new ArrayList<>();

        Member member = memberRepository.findById(id);
        qnaList = qnaRepository.findByMemberNum(member.getNum());

        return qnaList;
    }

    public Page<Qna> getqnaMng(int page) {
        Pageable pageable = PageRequest.of(page, 8);

        return this.qnaRepository.findAll(pageable);
    }

    public void qnaDelete(Long id){
        qnaRepository.deleteById(id);
    }
}