package com.example.legia.mobileweb.Database.Firebase.DTO;

import java.io.Serializable;

public class preOrder implements Serializable{
    int iduser;
    String ho_user;
    String ten_user;
    int sdt;
    String email;

    public preOrder() {

    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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
