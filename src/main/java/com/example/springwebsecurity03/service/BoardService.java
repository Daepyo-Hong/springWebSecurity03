package com.example.springwebsecurity03.service;

import com.example.springwebsecurity03.domain.dto.BoardSaveDTO;
import org.springframework.ui.Model;

public interface BoardService {
    void getListAll(int page, Model model);

    void sendDetail(long bno, Model model);

    void save(BoardSaveDTO dto);

    void delete(long bno);
}
