package com.busanit.petgathering.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.busanit.petgathering.constant.QnaStatus;
import com.busanit.petgathering.dto.QnaDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //이 클래스를 엔티티로 선언
@Table(name="qna") //엔티티와 매핑할 테이블을 지정
@Getter
@Setter
@ToString
public class Qna extends BaseEntity {

    @Id
    @Column(name = "qna_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_num")
    private Member member;

    @Column(nullable = false, length = 50) //필드와 컬럼을 매칭
    private String qnaNm;

    @Column(nullable = false, length = 50) //필드와 컬럼을 매칭
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Lob
    @Column(name = "reply")
    private String reply;

    @Enumerated(EnumType.STRING) //Enum 타입 매칭
    private QnaStatus qnaStatus;

    public void updateQna(QnaDto qnaDto) {
        this.reply = qnaDto.getReply();
        this.qnaStatus = qnaDto.getQnaStatus();
    }

    public static Qna createQna(QnaDto qnaDto, Member member) {
        Qna qna =  new Qna();
        qna.setMember(member);
        qna.setQnaNm(qnaDto.getQnaNm());
        qna.setTitle(qnaDto.getTitle());
        qna.setContent(qnaDto.getContent());
        qna.setReply(qnaDto.getReply());
        qna.setQnaStatus(QnaStatus.WAITING);
        return qna;
    }
}
