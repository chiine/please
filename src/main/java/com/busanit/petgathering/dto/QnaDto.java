package com.busanit.petgathering.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.busanit.petgathering.constant.QnaStatus;
import com.busanit.petgathering.entity.Qna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaDto {

    private Long id;

    @NotEmpty(message = "이름은 필수 입력 값입니다.")
    private String qnaNm;

    @NotEmpty(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 입력 값입니다.")
    private String content;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private String reply;

    private QnaStatus qnaStatus;

    private static ModelMapper modelMapper = new ModelMapper();

    public static QnaDto of(Qna qna){
        return modelMapper.map(qna, QnaDto.class);
    }

    private List<Qna> qnaList = new ArrayList<>();
}

