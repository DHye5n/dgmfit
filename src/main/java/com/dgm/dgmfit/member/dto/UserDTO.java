package com.dgm.dgmfit.member.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor //필드 모두를 매개변수로 하는 생성자 생성
public class UserDTO { //회원정보에 필요한 필드 정의

    //필드
    private int id;
    private String userID;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userPhone;
    private String userPost;
    private String userAddr;
    private String addr_detail;



}
