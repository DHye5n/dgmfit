package com.dgm.dgmfit.board.dto;


import lombok.Getter;



@Getter
public class BoardDTO {
    private int boardID;
    private int autoID;
    private String userName;
    private String boardName;
    private String boardTime;
    private String boardDivide;
    private String boardContents;

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public int getAutoID() {
        return autoID;
    }

    public void setAutoID(int autoID) {
        this.autoID = autoID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardTime() {
        return boardTime;
    }

    public void setBoardTime(String boardTime) {
        this.boardTime = boardTime;
    }

    public String getBoardDivide() {
        return boardDivide;
    }

    public void setBoardDivide(String boardDivide) {
        this.boardDivide = boardDivide;
    }

    public String getBoardContents() {
        return boardContents;
    }

    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }

    public BoardDTO(int boardID, int autoID, String userName, String boardName, String boardTime, String boardDivide, String boardContents) {
        this.boardID = boardID;
        this.autoID = autoID;
        this.userName = userName;
        this.boardName = boardName;
        this.boardTime = boardTime;
        this.boardDivide = boardDivide;
        this.boardContents = boardContents;
    }


}
