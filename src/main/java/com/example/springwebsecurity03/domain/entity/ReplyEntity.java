package com.example.springwebsecurity03.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SequenceGenerator(name = "gen_seq_re", sequenceName = "seq_re", allocationSize = 1)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "j_reply")
@Entity
public class ReplyEntity extends BaseDateTimeColumn {
    @Id
    @GeneratedValue(generator = "gen_seq_re",strategy = GenerationType.SEQUENCE)
    private long rno;

    @Column(nullable = false)
    private String text;

    //작성자 fk
    @JoinColumn(name = "mno", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH)    //멤버엔티티에 영향 X
    private MemberEntity member;

    //게시글 fk
    @JoinColumn(name = "bno", nullable = false)
    @ManyToOne(cascade = CascadeType.DETACH)
    private BoardEntity board;
}
