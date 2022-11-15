package com.example.springwebsecurity03.controller;

import com.example.springwebsecurity03.domain.dto.BoardListDTO;
import com.example.springwebsecurity03.domain.dto.BoardSaveDTO;
import com.example.springwebsecurity03.security.MyUserDetails;
import com.example.springwebsecurity03.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class BoardController {

    @Autowired
    private BoardService service ;

    @GetMapping("/boards") // ?page=1 : 쿼리스트링 파라미터가 존재하지 않을경우 예외발생:@RequestParam(defaultValue = "1")넣어서 해결
    public String board(@RequestParam(defaultValue = "1") int page, Model model){//문자열로 파라미터 매핑 --> int형 parse
        service.getListAll(page, model);
        return "board/list";
    }

    @GetMapping("/boards/{bno}")
    public String detail(@PathVariable long bno,Model model){
        service.sendDetail(bno,model);
        return "board/detail";
    }

    @GetMapping("/boards/write")
    public String write(){
        return "board/write";
    }


    @PostMapping("/boards")                 //Principal principal 객체도 가능 username 정보만 확인가능
    public String write_done(BoardSaveDTO dto, Authentication auth){
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        dto.setMno(myUserDetails.getMno());
        service.save(dto);
        return "redirect:/boards";
    }

    //삭제
    @DeleteMapping("/boards/{bno}")
    public String delete(@PathVariable long bno){
        service.delete(bno);
        return "redirect:/boards";
    }
}
