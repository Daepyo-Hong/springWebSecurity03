package com.example.springwebsecurity03.domain.dto;


import com.example.springwebsecurity03.domain.entity.ReplyEntity;
import lombok.Getter;

import java.time.LocalDateTime;

//상세페이지에 표현할 댓글 데이터 : Entity -> DTO 매핑
//Entity객체를 페이지에 가져가면 보안, 사용법 까다로움
@Getter
public class ReplyListDTO {
    private long rno;
    private String text;
    //작성자: 회원의 이메일로 적용할 것
    private String writer;

    private LocalDateTime updatedDate;

    public ReplyListDTO(ReplyEntity e) {
        this.rno = e.getRno();
        this.text = e.getText();
        this.writer = e.getMember().getEmail();
        this.updatedDate = e.getUpdatedDate();
    }
}
