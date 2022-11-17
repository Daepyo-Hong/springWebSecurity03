package com.example.springwebsecurity03.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter     //Entity 에 mapping 할 때 필요
@Setter     //Controller 파라미터 매핑을 위해 Setter 메서드 필요
public class BoardUpdateDTO {
    private String title;
    private String content;
}
