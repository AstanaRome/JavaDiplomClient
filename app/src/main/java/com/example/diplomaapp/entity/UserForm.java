package com.example.diplomaapp.entity;



public class UserForm {
    private User user;
    private String changeColumn;

    public UserForm() {
    }


    public UserForm(User user, String changeColumn) {
        this.user = user;
        this.changeColumn = changeColumn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChangeColumn() {
        return changeColumn;
    }

    public void setChangeColumn(String changeColumn) {
        this.changeColumn = changeColumn;
    }
}