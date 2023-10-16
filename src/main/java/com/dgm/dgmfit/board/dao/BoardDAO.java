package com.dgm.dgmfit.board.dao;

import com.dgm.dgmfit.board.dto.BoardDTO;
import com.dgm.dgmfit.board.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class BoardDAO {
    public ArrayList<BoardDTO> boardAllList (){
        ArrayList<BoardDTO> boardList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT boardID, autoID, userName, boardName, boardTime, boardDivide, boardContents FROM BOARD";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                BoardDTO board = new BoardDTO(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                boardList.add(board);
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
        return boardList;
    }

    //보드 아이디와 일치한 정보 가져오기
    public BoardDTO selectBoard (String boardID) {
        BoardDTO board = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT boardID, autoID, userName, boardName, boardTime, boardDivide, boardContents FROM BOARD WHERE boardID = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, boardID);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                board = new BoardDTO(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
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
        return board;
    }

    public int write(BoardDTO board) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "INSERT INTO BOARD VALUES(null, ?, ?, ?, ?, ?, ?)";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, board.getAutoID());
            pstmt.setString(2, board.getUserName());
            pstmt.setString(3, board.getBoardName());
            pstmt.setString(4, board.getBoardTime());
            pstmt.setString(5, board.getBoardDivide());
            pstmt.setString(6, board.getBoardContents());
            return pstmt.executeUpdate();
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
        return -2;
    }

    public int boardUpdate (String boardID, String boardDivide, String boardName, String boardContents, String boardTime) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE BOARD SET boardDivide = ?, boardName = ?, boardContents = ?, boardTime = ? WHERE boardID = ?";

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, boardDivide);
            pstmt.setString(2, boardName);
            pstmt.setString(3, boardContents);
            pstmt.setString(4, boardTime);
            pstmt.setString(5, boardID);
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

    public int boardDelete (String autoID, String userPassword, String boardID) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sqlUser = "SELECT userPassword FROM USERINFO where autoID = ?";
        String sqlBoard = "DELETE FROM BOARD WHERE boardID = ?";
        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sqlUser);
            pstmt.setString(1, autoID);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getString(1).equals(userPassword)) {
                } else {
                    return -1;
                }
            }
            pstmt.close();

            pstmt = conn.prepareStatement(sqlBoard);
            pstmt.setString(1, boardID);
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
}