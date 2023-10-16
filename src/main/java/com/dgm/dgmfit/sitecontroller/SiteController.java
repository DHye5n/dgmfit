package com.dgm.dgmfit.sitecontroller;

import com.dgm.dgmfit.board.dao.BoardDAO;
import com.dgm.dgmfit.board.dto.BoardDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class SiteController {

    //메인 페이지
    @GetMapping("/")
    public String MainPage(Model model) {
        return "member/main";
    }

    //로그인 페이지
    @GetMapping("login.html")
    public String loginPage(Model model , HttpSession session) {
        if(session.getAttribute("userID") != null) {
            return "member/main";
        }
        return "member/login";
    }

    @GetMapping("terms.html")
    public String term() { return "member/terms"; }

    //회원가입 페이지
    @GetMapping("register.html")
    public  String Register(Model model, HttpSession session) {
        if(session.getAttribute("userID") != null) {
            return "member/main";
        }
        return "member/register";
    }

    //로그아웃 페이지
    @GetMapping("userLogout.html")
    public  String logOut(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("message", "로그아웃 완료");
        return "member/main";
    }

    //아이디 찾기 페이지 view 요청값(href="find_id.html")
    @GetMapping("find_id.html")
    public String findID () { return "member/find_id"; }

    //비밀번호 찾기 페이지 view 요청값(href="find_pwd.html")
    @GetMapping("find_pwd.html")
    public String findPW () { return "member/find_pwd"; }

    //유저정보 페이지
    @GetMapping("mypage.html")
    public String userInfo () {
        return "member/mypage";
    }

    //고객센터 사이트 이동 메소드 내부에 보드리스트 불러오는 구문 필요
    @GetMapping("serviceCenter.html")
    public String serviceCenter (Model model) {
        BoardDAO boardDAO = new BoardDAO();
        ArrayList<BoardDTO> boardList = boardDAO.boardAllList();
        model.addAttribute("boardList", boardList);
        return "board/serviceCenter";
    }

    //글쓰기 페이지 이동
    @GetMapping("boardWrite")
    public String boardWrite () { return "board/boardWrite"; }

    //선택 게시글 board페이지 이동
    @GetMapping("selectBoard")
    public String selectBoard (Model model, HttpSession session,@RequestParam("boardID") String boardID) {
        BoardDAO boardDAO = new BoardDAO();
        BoardDTO selectBoard = boardDAO.selectBoard(boardID);
        if(selectBoard != null) {
            int autoID = Integer.parseInt((String) session.getAttribute("autoID"));
            model.addAttribute("autoID", autoID);//세션 고유 아이디를 셀렉 보더 고유 아이디랑 타입 맞추기 위한 작업
            model.addAttribute("selectBoard", selectBoard);
            return "board/board";
        } else {
            model.addAttribute("message", "불러오기 실패");
            return "board/serviceCenter";
        }
    }

    @GetMapping("boardDelete")
    public String boardDelete(Model model, @RequestParam("boardID") String boardID, @RequestParam("boardName") String boardName) {
        model.addAttribute("boardID", boardID);
        model.addAttribute("boardName", boardName);
        return "board/boardDelete";
    }
}

