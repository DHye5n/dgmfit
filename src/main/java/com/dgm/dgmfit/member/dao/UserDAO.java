package com.dgm.dgmfit.member.dao;

import com.dgm.dgmfit.board.util.DatabaseUtil;
import com.dgm.dgmfit.member.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
    public int login (String userID, String userPassword) {
        String sql = "SELECT userPassword, userName FROM USERINFO Where userID = ?";
        Connection conn = null;// 데이터베이스 서버에 연결을 생성합니다. 연결 객체를 통해 데이터베이스와의 통신을 수행(유틸 파일)
        PreparedStatement pstmt = null;// SQL 쿼리를 미리 컴파일하고 나중에 실행할 때 매개 변수를 바인딩(?, ?)
        ResultSet rs = null;//@@@@@ Resultset이 있어야 디비에서 처리된 값을 받아올수 있다
        try {
            conn = DatabaseUtil.getConnection();//연결 객체
            pstmt = conn.prepareStatement(sql);// 객체로, 미리 컴파일된 SQL 문,
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();// 데이터베이스로부터 데이터를 검색하는 메서드
            if(rs.next()) {//다음 레코드(행)를 가리킵니다. 만약 다음 행이 존재한다면(next()가 true를 반환하면), 그 행의 데이터를 읽을 수 있습니다.
                if(rs.getString(1).equals(userPassword)){
                    //현재 행에서 두 번째(인덱스0은 값이 없다 특징, 인덱스 1"디비에서 가져온 저장된 비번)) 열의 데이터를 가져와서 사용자가 입력한 비밀번호와 비교
                    return 1; //로그인 성공
                } else {
                    return 0; //비밀번호 불일치
                }
            }
            return -1; //아이디 존재 x
        } catch(Exception e) {
            e.printStackTrace();//예외 정보 기록 트라이 캐치 특성:  예외가 발생해도 프로그램은 종료되지 않고 예외를 처리할 수 있습니다.
        } finally {//무조건 실행 아래  자원을 닫다가 예외가 발생해도 프로그램은 종료되지 않고 예외를 처리
            try { if(conn != null) conn.close(); }//close  메모리 누수와 같은 문제를 방지하며, 코드의 신뢰성을 높입
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(rs != null) rs.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        return -2; //데이터 베이스 오류
    }

    //로그인 성공시 유저 이름 가져오는 구문 위와 통합 개발중
    public ArrayList<String> info(String userID) {
        String sql = "SELECT userID, userEmail, userName, userPhone, userPost, userAddr, addr_detail, autoID FROM USERINFO WHERE userID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<String> userInfoList = new ArrayList<>();// MAP활용 가능 ex( 성별 남자, 그 관련 모든값)
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                userInfoList.add(rs.getString("userID"));
                userInfoList.add(rs.getString("userEmail"));
                userInfoList.add(rs.getString("userName"));
                userInfoList.add(rs.getString("userPhone"));//Uint 활용? 넘버 오버플로우 발생시 활용 필요, 일단 롱부터
                userInfoList.add(rs.getString("userPost"));
                userInfoList.add(rs.getString("userAddr"));
                userInfoList.add(rs.getString("addr_detail"));
                userInfoList.add(rs.getString("autoID"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(rs != null) rs.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        return userInfoList;
    }
    public int idCheck (String checkID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sqlSelect = "SELECT COUNT(userID) FROM USERINFO WHERE userID = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, checkID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);//rs의 첫번쨰 인덱스값= 위에 쿼리값 가져온거로 생긴 count수 을 count에 정수형으로 저장
                if (count > 0) {//아이디가 하나라도 있으면 중복
                    return -1; // 중복 아이디인 경우
                } else {
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(rs != null) rs.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }

        return -2; // 데이터베이스 오류
    }

    public int join (UserDTO user) {//클래스형 파라미터 선언, join(new UserDTO());선언시 = UserDTO user = new UserDTO(); 와 같은 형식
        String sqlSelect = "SELECT COUNT(userID) FROM USERINFO WHERE userID = ?";
        String sqlInsert = "INSERT INTO USERINFO VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";//(null(디비에서 값 자동지정), userID, userEmail, us...)
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {//혹시 몰라 안전용으로 중복체크 하나더 만들어둠
            conn = DatabaseUtil.getConnection();//데이터베이스 유틸 클래스 파일에 있는 겟 커넥션 메소드를 불러와서 쓴다
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, user.getUserID());
            rs = pstmt.executeQuery();
            if (rs.next()) {//ResultSet에서 가져온 첫번째 인덱스는 아무런 값이 없다(이건 프로그램 내부 문제이다)
                int count = rs.getInt(1);//rs의 첫번쨰 인덱스값 비버있음 그다음값인 = 위에 쿼리값 가져온거로 생긴 count수 을 count에 정수형으로 저장
                if (count > 0) {//아이디가 하나라도 있으면 중복
                    return -1; // 중복 아이디인 경우
                    //중복 체크는 다른 방법도 많다
                }
            }

            pstmt.close(); // 기존의 pstmt 닫고 새로 생성 하나의 행동이 끝나면 닫아줘야 한다 간단하게 코드로 ;닫아주는 느낌

            pstmt = conn.prepareStatement(sqlInsert);//sqlInsert로 선언한 쿼리문 가져옴
            pstmt.setString(1, user.getUserID());//천번쨰 "?" **쿼리문의 값 입력 순서는 내가 디비에 직접 넣어야 하는값과 동일 해야한다
            pstmt.setString(2, user.getUserEmail());//두번
            pstmt.setString(3, user.getUserPassword());//암튼 DTO에 저장된 값들 가져와서 대입한다
            pstmt.setString(4, user.getUserName());
            pstmt.setString(5, user.getUserPhone());//스트링인 이유 폰번호 인트로하면 그n자리 수가 되어서
            pstmt.setString(6, user.getUserPost());
            pstmt.setString(7, user.getUserAddr());
            pstmt.setString(8, user.getAddr_detail());
//        pstmt.setDate(7, new java.sql.Date(user.getUserBirth().getTime()));

            return pstmt.executeUpdate();//데이터베이스에 영향을 미치는 SQL 쿼리(INSERT, UPDATE, DELETE)를 실행
            //리턴에다 쓰면 리턴값이 1같은 정수형으로 나옴
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(rs != null) rs.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }

        return -2; // 데이터베이스 오류
    }
    public int infoUpdate (String userID, String userEmail, String userPhone, String userPost, String userAddr, String addr_detail) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE USERINFO SET userEmail = ?, userPhone = ?, userPost = ?, userAddr = ?, addr_detail = ? WHERE userID = ?";

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userEmail);
            pstmt.setString(2, userPhone);
            pstmt.setString(3, userPost);
            pstmt.setString(4, userAddr);
            pstmt.setString(5, addr_detail);
            pstmt.setString(6, userID);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if(conn != null) conn.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        return -2;
    }

    public String findID (String userName, String userEmail, String userPhone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT userID FROM USERINFO WHERE userName = ? AND userEmail = ? AND userPhone = ?";
        String ID = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, userEmail);
            pstmt.setString(3, userPhone);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                ID = rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try { if(conn != null) conn.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        return ID;
    }

    public String findPW (String userID, String userName, String userPhone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT userPassword FROM USERINFO WHERE userID = ? AND userName = ? AND userPhone = ?";
        String PW = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, userName);
            pstmt.setString(3, userPhone);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                PW = rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try { if(conn != null) conn.close(); }
            catch (Exception e) { e.printStackTrace(); }
            try { if(pstmt != null) pstmt.close(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        return PW;
    }

}
