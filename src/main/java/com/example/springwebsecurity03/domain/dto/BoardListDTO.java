package com.example.springwebsecurity03.domain.dto;

import com.example.springwebsecurity03.domain.entity.BoardEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BoardListDTO {
    private long bno;
    private String title;
    private int readCount;
    private String writer;
    private LocalDateTime updatedDate;
    private LocalDate today;

    //Entity를 -> BoardListDTO(BoardEntity ent)
    public BoardListDTO(BoardEntity ent) {
        this.bno = ent.getBno();
        this.title = ent.getTitle();
        this.readCount = ent.getReadCount();
        this.writer = ent.getMember().getEmail()+"("+ent.getMember().getName()+")";
        this.updatedDate = ent.getUpdatedDate();
        today=LocalDate.now();
    }
}
