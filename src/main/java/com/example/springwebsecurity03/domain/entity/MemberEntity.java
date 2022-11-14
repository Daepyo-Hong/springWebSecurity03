package com.example.springwebsecurity03.domain.entity;

import com.example.springwebsecurity03.security.MyRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor     //빌더패턴 적용하려고
@NoArgsConstructor      //
@Getter
@SequenceGenerator(name = "g_mem_seq",
        sequenceName = "mem_seq", initialValue = 1,allocationSize = 1)
@Table(name = "mem")
@Entity
public class MemberEntity extends BaseDateTimeColumn {


    @Id
    @GeneratedValue(generator = "g_mem_seq", strategy = GenerationType.SEQUENCE)
    private long mno;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pass;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String nickName;

    //social user 여부
    //탈퇴여부 필드
    //휴면여부 필드
    //user ip 등 회원가입에 필요한 사항들 추가로 적용

    // 1:M -- 회원:MyRole(enum)
    @Enumerated(EnumType.STRING) //저장시 문자열로 저장(확장시 용이)
    @Builder.Default //new HashSet<>(); 빌더적용시 default로 사용하기 위해
    @CollectionTable(name = "my_role")  //테이블 명 설정
    @ElementCollection(fetch = FetchType.EAGER)//MyRole 즉시로딩
    private Set<MyRole> roles = new HashSet<>();
    //임베디드로 들어가는거라 멤버테이블 repository 로만 접근가능하기때문에
    //편의 메서드 사용하면 Set콜렉션 사용이 편리
    public MemberEntity addRole(MyRole role){
        roles.add(role);
        return this;
    }
    public MemberEntity removeRole(MyRole role){
        roles.remove(role);
        return this;
    }

}
