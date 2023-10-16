//package com.dgm.dgmfit.member.controller;
//
//import com.dgm.dgmfit.member.dto.UserDTO;
//import com.dgm.dgmfit.member.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
///*
//회원가입 로그인 관련 컨트롤러
//*/
//@Controller  //해당 클래스가 컨트롤러임을 알리고 bean으로 등록하기 위함
//@RequiredArgsConstructor //의존관계 때문에 필요함
//public class UserController {
//    //생성자 주입
//    //MemberService 클래스 객체를 주입받음(권한을 받아옴)
//    private final MemberService memberService;
//
//
//    //회원가입 페이지 출력 요청
//    @GetMapping("/member/save") //status=404에러 주소가 없음, status=405에러 방식이 다를 때(post로 요청했으나 get으로 받을 때)
//    public String saveForm() {
//        return "member/save";
//    }
//
//    @PostMapping("/member/save")
//    public String save(@ModelAttribute UserDTO memberDTO) { //받을 때 MemberDTO에 있는 변수들을 한번에 받음
//        System.out.println("MemberController.save"); //멤버컨트롤러의 세이브 메서드가 제대로 실행되는지 확인
//        System.out.println("memberDTO =" + memberDTO); //memberDTO 매개변수 잘 받아오는지 확인
//        memberService.save(memberDTO); //기존 방식은 memberService객체를 생성하여 하지만 잘 쓰지 않고 MemberService클래스에서 메서드만 받아온다
//        return "member/login"; //회원가입이 정상적으로 이루어졌을 경우 로그인페이지로 이동
//    }
//
//    //로그인 페이지 출력 요청
//    @GetMapping("/member/login")
//    public String loginForm() {
//        return "member/login";
//    }
//
//    @PostMapping("/member/login")
//    public String login(@ModelAttribute UserDTO memberDTO, HttpSession session) {
//        UserDTO loginResult = memberService.login(memberDTO);
//        if (loginResult != null) {
//            //login 성공
//            String name;
//            session.setAttribute("loginId", loginResult.getUserID());
//            return "member/main";
//        } else {
//            //login 실패
//            return "member/login";
//        }
//    }
//
//    //회원 목록 조회(DB에 저장된 회원 모두 가져옴) **관리자전용**
//    @GetMapping("/member/list")
//    public String findAll(Model model) { //Model : servlet에서 request영역을 담당하는 객체
//        List<UserDTO> memberDTOList = memberService.findAll();
//        //어떠한 html로 가져갈 데이터가 있다면 model 사용
//        model.addAttribute("memberList", memberDTOList);
//        return "list";
//    }
//
//    //회원 정보 상세 조회 **관리자전용**
//    @GetMapping("/member/{id}")
//    public String findById(@PathVariable Long id, Model model) {//경로상에 있는 값을 받아옴
//        UserDTO memberDTO = memberService.findById(id);
//        model.addAttribute("memeber", memberDTO);
//        return "detail";
//    }
//
//    //회원 정보 수정
//    @GetMapping("/member/update")
//    public String updateForm(HttpSession session, Model model) {
//       String myuserID = (String)session.getAttribute("loginuserID"); //강제형변환해줘야함 session은 Object타입
//        UserDTO memberDTO = memberService.updateForm(myuserID);
//       model.addAttribute("updateMember", memberDTO);
//       return "update";
//    }
//
//    @PostMapping("/member/update")
//    public String update(@ModelAttribute UserDTO memberDTO) {
//        memberService.update(memberDTO);
//        return "redirect:/member/" + memberDTO.getId(); //컨트롤러 메서드 처리가 끝나고 다시 다른 컨트롤러 메서드를 호출하는 방식
//    }
//
//    //회원 탈퇴(관리자 전용)
//    @GetMapping("/member/delete/{id}")
//    public String deleteById(@PathVariable Long id) {
//        memberService.deleteById(id);
//        return "redirect:/member/";
//    }
//
//    //로그아웃
//    @GetMapping("/member/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();  //세션무효화
//        return "index";
//    }
//
//    //회원가입 아이디 중복
//    @PostMapping("/member/user-id-check")
//    public @ResponseBody String userIDCheck(@RequestParam("userID") String userID) {//@ResponseBody : ajax사용시 필수
//        System.out.println("userID = " + userID);
//        String idCheckResult = memberService.userIDCheck(userID);
//        if (idCheckResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
//    }
//
//}
