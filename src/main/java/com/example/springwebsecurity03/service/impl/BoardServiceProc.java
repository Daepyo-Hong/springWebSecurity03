package com.example.springwebsecurity03.service.impl;

import com.example.springwebsecurity03.domain.dto.*;
import com.example.springwebsecurity03.domain.entity.*;
import com.example.springwebsecurity03.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceProc implements BoardService {


    private final BoardEntityRepository repository;

    private final ReplyEntityRepository replyRepo;

    //게시글 리스트
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

    //게시글 상세정보
    @Override
    public void sendDetail(long bno, Model model) {

        //Id: pk컬럼

        model.addAttribute("detail", repository.findById(bno)
                .map(BoardDetailDTO::new)
                .orElseThrow());
        replyRepo.findAllByBoardBnoOrderByRnoDesc(bno);

        model.addAttribute("replies",replyRepo.findAllByBoardBnoOrderByRnoDesc(bno)
                .stream()
                .map(ReplyListDTO::new)
                .collect(Collectors.toList()));//List<ReplyListDTO>
    }

    //게시글 생성
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

    //게시글 삭제
    @Override
    public void delete(long bno) {
        //bno : pk 이므로 !
        repository.deleteById(bno);
    }

    //게시글 수정
    @Override
    public void update(long bno, BoardUpdateDTO dto) {
        //수정처리 대상은 bno, 수정할 데이터는 dto:제목,내용
        //1. 대상의 존재여부
        Optional<BoardEntity> result = repository.findById(bno);

        //2. 존재하면 수정
        if(result.isPresent()){
            BoardEntity entity = result.get();
            //Setter 이용해도 되지만 수정하기위한 편의메서드 생성해서 해봄
            entity.update(dto);
            //업데이트 반영된 entity 저장
            repository.save(entity);    //이미 존재하는 PK이면 수정반영됩니다.
        }
        //BoardEntity entity = repository.findById(bno).orElseThrow();
        //entity.setTitle(dto.getTitle());
        //entity.setContent(dto.getContent());

    }

    //수정처리 간결하게 가능함...
    @Transactional //repository.findById(bno)까지 하고 DB연결이 끊기는데 그걸 방지하기 위해..
    @Override
    public void updateProc(long bno, BoardUpdateDTO dto) {
        System.out.println(">>>>>>>>>>>수정처리");
        //디비 연결이 살아있기 때문에 엔티티 변경여부가 반영이 됩니다. 커밋이 메서드 끝날 때 일어남.
        repository.findById(bno).map(entity-> entity.update(dto));
    }   //@Transactional 메서드가 끝나면 commit 이루어지므로 이전에 Entity 객체가 변경되면 update 반영
}
