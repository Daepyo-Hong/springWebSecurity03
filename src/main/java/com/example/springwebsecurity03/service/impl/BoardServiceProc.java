package com.example.springwebsecurity03.service.impl;

import com.example.springwebsecurity03.domain.dto.BoardDetailDTO;
import com.example.springwebsecurity03.domain.dto.BoardListDTO;
import com.example.springwebsecurity03.domain.dto.BoardSaveDTO;
import com.example.springwebsecurity03.domain.entity.BoardEntity;
import com.example.springwebsecurity03.domain.entity.BoardEntityRepository;
import com.example.springwebsecurity03.domain.entity.MemberEntity;
import com.example.springwebsecurity03.domain.entity.MemberEntityRepository;
import com.example.springwebsecurity03.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceProc implements BoardService {


    private final BoardEntityRepository repository;


    @Override
    public void getListAll(int page, Model model) {
        //Entity자체(DB 테이블 자체)를 모델에 넣으면 다른 값(pass) 등을 참조할 수 있으므로
        //DTO로 매핑해서 가져가는것이 맞다..!

        //int page=1;
        int size = 10;
        Sort sort = Sort.by(Sort.Direction.DESC, "bno");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<BoardEntity> result = repository.findAll(pageable);
        //Pageable pageable=PageRequest.of(page-1,size);
        //Page<BoardEntity> result = repository.findAllByOrderByBnoDesc(pageable);

        //List<BoardEntity> list = result.getContent();
        //int pageTot = result.getTotalPages();

        model.addAttribute("p", result);
        /*
        System.out.println("getNumber():"+result.getNumber());      //페이지번호
        System.out.println("getNumberOfElements():"+result.getNumberOfElements());  //페이지에담겨있는요소개수
        System.out.println("isFirst():"+result.isFirst());
        System.out.println("isLast():"+result.isLast());
        System.out.println("hasNext():"+result.hasNext());
        System.out.println("hasContent():"+result.hasContent());
        System.out.println("hasPrevious():"+result.hasPrevious());
        */
        model.addAttribute("list", result.stream()
                //.map(ent->new BoardListDTO(ent))
                .map(BoardListDTO::new)
                .collect(Collectors.toList()));
        /*
        model.addAttribute("list",repository.findAllByOrderByBnoDesc().stream()
                //.map(ent->new BoardListDTO(ent))
                .map(BoardListDTO::new)
                .collect(Collectors.toList()) );
        */
    }

    @Override
    public void sendDetail(long bno, Model model) {

        //Id: pk컬럼

        model.addAttribute("detail", repository.findById(bno)
                .map(BoardDetailDTO::new)
                .orElseThrow());
    }

    @Override
    public void save(BoardSaveDTO dto) {
        //repository.save(dto.toBoardEntity());

        BoardEntity entity = BoardEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(MemberEntity.builder()
                        .mno(dto.getMno())
                        .build())
                .build();
        repository.save(entity);
    }

    @Override
    public void delete(long bno) {
        //bno : pk 이므로 !
        repository.deleteById(bno);
    }
}
