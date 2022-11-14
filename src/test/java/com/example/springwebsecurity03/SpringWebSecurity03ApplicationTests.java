package com.example.springwebsecurity03;

import com.example.springwebsecurity03.domain.entity.BoardEntity;
import com.example.springwebsecurity03.domain.entity.BoardEntityRepository;
import com.example.springwebsecurity03.domain.entity.MemberEntity;
import com.example.springwebsecurity03.security.MyRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringWebSecurity03ApplicationTests {

    @Autowired
    BoardEntityRepository bRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    void 회원가입(){

    }

    @Test
    void 게시글저장() {
        /*  //게시글을 저장할 때 
        MemberEntity me = MemberEntity.builder()
                .email("test01@test.com").name("테스트01").pass(encoder.encode("1234"))
                .build().addRole(MyRole.USER);
        */
        BoardEntity entity=BoardEntity.builder()
                .title("관리자가쓴글2").content("관리자내용2")
                //.member(me)
                .build();
        bRepository.save(entity);
    }

}
