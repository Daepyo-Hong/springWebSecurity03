package com.example.springwebsecurity03.security;

import com.example.springwebsecurity03.domain.entity.MemberEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

//org.springframework.security.core.userdetails.User 활용해서 만든 UserDetails 클래스
@Getter
public class MyUserDetails extends User {
    //필요한 데이터 커스터마이징 가능, principal 요소
    private long mno;   //pk
    private String email;
    private String name;
    private String nickName;
    private boolean admin;


    public MyUserDetails(MemberEntity entity) {
        super(entity.getEmail(), entity.getPass(), entity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet()));
        //stream을 이용하면 안쪽 구성요소를 바꿀 수 있어요.
        //entity.getRoles():Set<MyRole> -> Collection<SimpleGrantedAuthority>
        mno = entity.getMno();        //pk
        email = entity.getEmail();    //이메일
        name = entity.getName();      //이름
        nickName = entity.getNickName();//닉네임

        for (MyRole role : entity.getRoles()) {
            if (role.name().equals("ADMIN")) {
                admin = true;
                break;
            }
        }
    }

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
