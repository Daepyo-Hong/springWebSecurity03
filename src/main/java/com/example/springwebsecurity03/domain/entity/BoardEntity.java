package com.example.springwebsecurity03.domain.entity;


import com.example.springwebsecurity03.domain.dto.BoardUpdateDTO;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


//@Setter
@Getter
@DynamicUpdate      //수정할 때 쿼리가 수정된 데이터만 들어가는 것
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "gen_j_board_seq",
            sequenceName = "j_board_seq", initialValue = 1, allocationSize = 1)
@Table(name = "j_board")
@Entity
public class BoardEntity extends BaseDateTimeColumn{
    @Id
    @GeneratedValue(generator = "gen_j_board_seq",strategy = GenerationType.SEQUENCE)
    private long bno;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private int readCount;
    //작성자 - MemberEntity    //

    //@JoinColumn 명시되면 주엔티티(부모)
    @JoinColumn(name = "mno")   //물리DB에 생성되는 FK컬럼명(디폴트 : 필드명_pk : member_mno)
    //부모엔티티에서 자식엔티티의 상태변화의 영향을 주는것 : cascade
    @ManyToOne(cascade = CascadeType.DETACH) // PERSIST: 저장시에만 전이 -->여기엔 맞지 않음
    // DETACH : 자식엔티티에 영향주지않는 readOnly역할
    //fetch 디폴트 eager(즉시로딩) : 1개니깐 ! collection 디폴트 : LAZY
    private MemberEntity member;    //작성자 정보

    public BoardEntity update(BoardUpdateDTO dto) {
        title=dto.getTitle();
        content=dto.getContent();
        return this;
    }
}
