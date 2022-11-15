package com.example.springwebsecurity03.domain.dto;

import com.example.springwebsecurity03.domain.entity.BoardEntity;
import com.example.springwebsecurity03.domain.entity.MemberEntity;
import com.example.springwebsecurity03.domain.entity.MemberEntityRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
public class BoardSaveDTO {

    private String title;
    private String content;
    //private String writer;
    private long mno;
    //private MemberEntity member;

    public BoardEntity toBoardEntity(){
        return BoardEntity.builder()
                .title(title)
                .content(content)
                .member(MemberEntity.builder()
                        .mno(mno)
                        .build())
                .build();
    }
}
