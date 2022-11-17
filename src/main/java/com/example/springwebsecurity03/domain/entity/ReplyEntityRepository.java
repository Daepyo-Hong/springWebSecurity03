package com.example.springwebsecurity03.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyEntityRepository extends JpaRepository<ReplyEntity,Long> {

    //만드는규칙 : 키워드 확인 + Entity의 컬럼명이나 객체명(카멜표현)
    List<ReplyEntity> findAllByBoardBnoOrderByRnoDesc(long bno);
}
