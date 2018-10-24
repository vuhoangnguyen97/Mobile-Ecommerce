package com.example.legia.mobileweb.DTO;

import java.io.Serializable;

public class userPreOrder implements Serializable {
    private String ho_user;
    private String ten_user;
    private int sdt;
    private String email;

    public userPreOrder() {
    }

    public String getHo_user() {
        return ho_user;
    }

    public void setHo_user(String ho_user) {
        this.ho_user = ho_user;
    }

    public String getTen_user() {
        return ten_user;
    }

    public void setTen_user(String ten_user) {
        this.ten_user = ten_user;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
