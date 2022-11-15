package com.example.springwebsecurity03.domain.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface BoardEntityRepository extends JpaRepository<BoardEntity,Long> {

    List<BoardEntity> findAllByOrderByBnoDesc();

    Page<BoardEntity> findAllByOrderByBnoDesc(Pageable pageable);
}
