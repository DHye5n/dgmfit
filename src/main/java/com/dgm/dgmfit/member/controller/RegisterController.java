package com.dgm.dgmfit.member.controller;

import com.dgm.dgmfit.member.dao.UserDAO;
import com.dgm.dgmfit.member.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class RegisterController {


    @PostMapping("/register")
    public String register(@RequestParam("userID") String userID,
                           @RequestParam("userEmail") String userEmail,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("userName") String userName,
                           @RequestParam("userPhone") String userPhone,
                           @RequestParam("userPost") String userPost,
                           @RequestParam("userAddr") String userAddr,
                           @RequestParam("addr_detail") String addr_detail,
                           HttpSession session,
                           Model model) {

        String loginID = null;
        loginID = (String) session.getAttribute("userID");

        if(loginID != null) {//위에서 실행이 이뤄지면 로그인완료 상태이니 다시 되돌리는 구문
            model.addAttribute("message", "이미 로그인이 되어 있는 상태 입니다");
            return "member/main";
        }

        //입력값 조건 지정 추가
        //공백 체크 값이 안넘어오면 VIEW에서 안쓴거니 필수값은 이리 체크
        if (userID == null || userEmail == null || userPassword == null || userName == null || userPhone == null || userID.equals("") || userEmail.equals("") || userPassword.equals("") || userName.equals("") || userPhone.equals("")) {
            model.addAttribute("message", "입력하지 않은 값이 있습니다");
            return "member/register";
        }

        UserDAO userDAO = new UserDAO();

        int result = userDAO.join(new UserDTO(0, userID, userEmail, userPassword, userName, userPhone, userPost, userAddr, addr_detail));

        if (result == -1) {
            model.addAttribute("message", "중복 아이디 입니다");
            return "member/register";
        }

        else if (result == -2) {
            model.addAttribute("message", "데이터 베이스 오류");
            return "member/register";
        }

        else {
            model.addAttribute("message", "회원가입 성공");
            return "member/login";
        }
    }

    @PostMapping("/infoUpdate")
    public String infoUpdate (Model model, HttpSession session,
                              @RequestParam("userEmail") String userEmail,
                              @RequestParam("userPhone") String userPhone,
                              @RequestParam("userPost") String userPost,
                              @RequestParam("userAddr") String userAddr,
                              @RequestParam("addr_detail") String addr_detail) {
        String userID = null;
        userID = (String) session.getAttribute("userID");

        UserDAO userDAO = new UserDAO();

        int result = userDAO.infoUpdate(userID, userEmail, userPhone, userPost, userAddr, addr_detail);
        if(result == -2){
            model.addAttribute("message", "등록실패, DB오류");
        } else {
            ArrayList<String> infoList = userDAO.info(userID);
            session.setAttribute("userID", infoList.get(0));
            session.setAttribute("userEmail", infoList.get(1));
            session.setAttribute("userName", infoList.get(2));
            session.setAttribute("userPhone", infoList.get(3));
            session.setAttribute("userPost", infoList.get(4));
            session.setAttribute("userAddr", infoList.get(5));
            session.setAttribute("addr_detail", infoList.get(6));
            model.addAttribute("message", "정보 수정 성공");
        }
        return "member/mypage";
    }

}
