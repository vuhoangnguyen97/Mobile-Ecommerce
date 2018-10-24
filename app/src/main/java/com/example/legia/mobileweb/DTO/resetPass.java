package com.example.legia.mobileweb.DTO;

import java.sql.Timestamp;

public class resetPass {
    private int iduser;
    private String tokenKey;
    private Timestamp create;

    public resetPass() {

    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public Timestamp getCreate() {
        return create;
    }

    public void setCreate(Timestamp create) {
        this.create = create;
    }
}
