package com.panabey.smartnotebook.Account;

public class User {

    public int ErrorCode;
    public String Username;
    public String LastName;
    public String FirstName;
    public String DateJoin;

    public User(int ErrorCode, String Username, String FirstName, String LastName, String DateJoin){
        this.ErrorCode = ErrorCode;
        this.Username = Username;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.DateJoin = DateJoin;
    }

    public String FullName(){
        return  FirstName + " " + LastName;
    }
}
