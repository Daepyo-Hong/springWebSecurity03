package com.example.springwebsecurity03.controller;

import com.example.springwebsecurity03.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService service ;

    @GetMapping("/boards")
    public String board(Model model){
        service.getListAll(model);
        return "board/list";
    }
}
