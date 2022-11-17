package com.example.springwebsecurity03;

import com.example.springwebsecurity03.domain.entity.*;
import com.example.springwebsecurity03.security.MyRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
class SpringWebSecurity03ApplicationTests {

    @Autowired
    BoardEntityRepository bRepository;

    @Autowired
    MemberEntityRepository mRepository;

    @Autowired
    ReplyEntityRepository rrepository;
    @Autowired
    PasswordEncoder encoder;

    @Test
    void 댓글저장(){
        long bno= 100L;   //댓글대상
        long mno= 1L;    //작성자pk
        ReplyEntity entity=ReplyEntity.builder()
                .text(bno+"게시글 댓글 2")
                .board(BoardEntity.builder().bno(bno).build())//게시글정보
                .member(MemberEntity.builder().mno(mno).build())
                .build();
        rrepository.save(entity);
    }


    @Test
    void 관리자() {
        String email = "admin@test.com";
        mRepository.save(MemberEntity.builder()
                .email(email)
                .name("관리자")
                .pass(encoder.encode("1234"))
                .nickName("admin")
                .build().addRole(MyRole.GUEST).addRole(MyRole.USER).addRole(MyRole.ADMIN));

    }


    @Test
    void 회원가입() {

        IntStream.rangeClosed(1, 20).forEach(i -> {
            String email = "test" + i + "@test.com";

            mRepository.save(MemberEntity.builder()
                    .email(email)
                    .name("테스트" + i)
                    .pass(encoder.encode("1234"))
                    .nickName("test" + i + "nick")
                    .build().addRole(MyRole.USER));
        });
    }

    @Test
    void 게시글저장() {

        mRepository.findAll().forEach(e -> {

            IntStream.rangeClosed(1, 5).forEach(i -> {
                bRepository.save(BoardEntity.builder()
                        .member(e)      //작성자
                        .title(e.getNickName() + "이 작성한 제목" + i)
                        .content(e.getNickName() + "이 작성한 내용" + i)
                        .build());
            });
        });


    }

}
