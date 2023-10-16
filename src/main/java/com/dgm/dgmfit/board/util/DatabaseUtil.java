package com.dgm.dgmfit.board.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    //그냥 연결문 양식
    public static Connection getConnection() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/db_dgmfit";//jdbc드라이버 mysql이용 //너 호스트번호/너의 활용 스키마
            String dbID = "root";//내 디비 아이디
            String dbPassword = "1234";//디비 비번
            Class.forName("com.mysql.jdbc.Driver");//드라이버에 연동 한다고 보면됨
            return DriverManager.getConnection(dbURL, dbID, dbPassword);//내가 사용할 디비주소 디비연결을위한 아이디 비번값 리턴(다른곳에서 이걸로 디비연결)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
