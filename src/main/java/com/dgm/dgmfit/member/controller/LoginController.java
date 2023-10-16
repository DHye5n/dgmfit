package com.dgm.dgmfit.member.controller;

import com.dgm.dgmfit.member.dao.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class LoginController {
    @PostMapping("/login")
    public String login(@RequestParam("userID") String userID,
                        @RequestParam("userPassword") String userPassword,
                        HttpSession session,
                        Model model) {

        String loginID = null;
        loginID = (String) session.getAttribute("userID");

        if (loginID != null) {
            model.addAttribute("message", "이미 로그인이 되어 있는 상태 입니다");
            return "member/main";
        }

        UserDAO userDAO = new UserDAO();
        int result = userDAO.login(userID, userPassword);

        if (result == 1) {
            ArrayList<String> infoList = userDAO.info(userID);
//         userID =  infoList.get(0);//추후 혹시나 값이 없어져 예외처리 발생시 basketID = selectList.isEmpty() ? -1 : selectList.get(0); 구문 예시 활용필요
            session.setAttribute("userID", infoList.get(0));
            session.setAttribute("userEmail", infoList.get(1));
            session.setAttribute("userName", infoList.get(2));
            session.setAttribute("userPhone", infoList.get(3));
            session.setAttribute("userPost", infoList.get(4));
            session.setAttribute("userAddr", infoList.get(5));
            session.setAttribute("addr_detail", infoList.get(6));
            session.setAttribute("autoID", infoList.get(7));

            if (userID != null && userID.equals("admin")) {
                model.addAttribute("message", "관리자로 로그인하셨습니다.");
            } else {
                model.addAttribute("message", "로그인 성공하셨습니다.");
            }
            return "member/main";
        } else if (result == 0) {
            model.addAttribute("message", "비밀번호가 다릅니다.");
        } else if (result == -1) {
            model.addAttribute("message", "아이디가 없습니다.");
        } else if (result == -2) {
            model.addAttribute("message", "DB에러.");
        }
        return "member/login";
    }

}