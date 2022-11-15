package com.example.springwebsecurity03;

import com.example.springwebsecurity03.domain.entity.BoardEntity;
import com.example.springwebsecurity03.domain.entity.BoardEntityRepository;
import com.example.springwebsecurity03.domain.entity.MemberEntity;
import com.example.springwebsecurity03.domain.entity.MemberEntityRepository;
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
    PasswordEncoder encoder;

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
