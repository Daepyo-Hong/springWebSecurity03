package com.example.springwebsecurity03.service.impl;

import com.example.springwebsecurity03.domain.dto.BoardListDTO;
import com.example.springwebsecurity03.domain.entity.BoardEntityRepository;
import com.example.springwebsecurity03.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceProc implements BoardService {


    private final BoardEntityRepository repository;
    @Override
    public void getListAll(Model model) {
        //Entity자체(DB 테이블 자체)를 모델에 넣으면 다른 값(pass) 등을 참조할 수 있으므로
        //DTO로 매핑해서 가져가는것이 맞다..!

        model.addAttribute("list",repository.findAll().stream()
                //.map(ent->new BoardListDTO(ent))
                .map(BoardListDTO::new)
                .collect(Collectors.toList()) );
    }
}
