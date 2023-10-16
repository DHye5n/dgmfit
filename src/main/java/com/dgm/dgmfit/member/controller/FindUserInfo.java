package com.dgm.dgmfit.member.controller;

import com.dgm.dgmfit.member.dao.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FindUserInfo {
    @PostMapping("/findUserID")
    public String findID (Model model,
                          @RequestParam("userName") String userName,
                          @RequestParam("userEmail") String userEmail,
                          @RequestParam("userPhone") String userPhone) {

        UserDAO userDAO = new UserDAO();

        String result = userDAO.findID(userName, userEmail, userPhone);
        if (result == null) {
            model.addAttribute("message", "정보와 일치하는 아이디가 없습니다");
            return "find_id";
        } else {
            model.addAttribute("message", result);
            return "login";
        }
    }

    @PostMapping("/findUserPw")
    public String findPW (Model model,
                          @RequestParam("userID") String userID,
                          @RequestParam("userName") String userName,
                          @RequestParam("userPhone") String userPhone) {

        UserDAO userDAO = new UserDAO();

        String result = userDAO.findPW(userID, userName, userPhone);
        if(result == null) {
            model.addAttribute("message", "정보 불일치");
            return "find_id";
        } else {
            model.addAttribute("message", result);
            return "login";
        }
    }

}
