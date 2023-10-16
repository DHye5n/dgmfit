package com.dgm.dgmfit.board.Controller;

import com.dgm.dgmfit.board.dao.BoardDAO;
import com.dgm.dgmfit.board.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class BoardController {
    @PostMapping("boardInsert")
    public String boardInsert (@RequestParam("boardName") String boardName,
                               @RequestParam("boardDivide") String boardDivide,
                               @RequestParam("boardContents") String boardContents,
                               Model model, HttpSession session) {
        int autoID = 0;
        String userName = null;
        autoID = Integer.parseInt((String) session.getAttribute("autoID"));
        userName = (String) session.getAttribute("userName");

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        String boardTime = dateTime.format(formatter);


        BoardDAO boardDAO = new BoardDAO();

        int result = boardDAO.write(new BoardDTO(0, autoID, userName, boardName, boardTime, boardDivide, boardContents));
        if(result == -2) {
            model.addAttribute("message", "등록 실패");
        } else {
            model.addAttribute("message", "등록 성공");
        }

        return "redirect:serviceCenter.html";
    }

    @PostMapping("boardUpdate")
    public String boardUpdate (@RequestParam("boardID") String boardID,
                               @RequestParam("boardDivide") String boardDivide,
                               @RequestParam("boardName") String boardName,
                               @RequestParam("boardContents") String boardContents,
                               Model model, HttpSession session) {

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        String boardTime = dateTime.format(formatter);

        BoardDAO boardDAO = new BoardDAO();

        int result = boardDAO.boardUpdate(boardID, boardName, boardTime, boardDivide, boardContents);
        if(result == -2) {
            model.addAttribute("message", "DB에러 발생");
        } else {
            BoardDTO selectBoard = boardDAO.selectBoard(boardID);
            model.addAttribute("selectBoard", selectBoard);
        }

        return "board/board";
    }

    @PostMapping("boardDelete")
    public String boardDelete (@RequestParam("boardID") String boardID,
                               @RequestParam("userPassword") String userPassword,
                               Model model, HttpSession session) {
        String autoID = null;
        autoID = (String) session.getAttribute("autoID");

        BoardDAO boardDAO = new BoardDAO();

        int result = boardDAO.boardDelete(autoID, userPassword, boardID);
        if(result == -2) {
            model.addAttribute("message", "DB에러 발생");
            BoardDTO selectBoard = boardDAO.selectBoard(boardID);
            model.addAttribute("selectBoard", selectBoard);
            return "board/board";
        } else if (result == -1) {
            model.addAttribute("message", "비밀번호 불일치");
            BoardDTO selectBoard = boardDAO.selectBoard(boardID);
            model.addAttribute("selectBoard", selectBoard);
            return "board/board";
        } else {
            model.addAttribute("message", "삭제 성공");
            return "redirect:/serviceCenter.html";
        }
    }

}
