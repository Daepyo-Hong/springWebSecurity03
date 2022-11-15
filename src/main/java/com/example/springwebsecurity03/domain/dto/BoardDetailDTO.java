package com.example.springwebsecurity03.domain.dto;

import com.example.springwebsecurity03.domain.entity.BoardEntity;
import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BoardDetailDTO {

    private long bno;
    private String title;
    private String content;
    private int readCount;
    private long writerMno;
    private String writerEmail;
    private String writerNickName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    public BoardDetailDTO(BoardEntity entity) {
        this.bno = entity.getBno();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.readCount = entity.getReadCount();
        this.writerMno = entity.getMember().getMno();
        this.writerEmail = entity.getMember().getEmail();    //작성자는 이메일 정보로 대체(닉네임 쓰는게 보통일듯)
        this.writerNickName = entity.getMember().getNickName();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }
}
