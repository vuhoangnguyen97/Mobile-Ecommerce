package com.example.legia.mobileweb.DTO;

public class gopY {
    private int idGopY;
    private int hinhGopY;
    private String tenGopY;


    public gopY(int idGopY, String tenGopY, int hinhGopY) {
        this.idGopY = idGopY;
        this.hinhGopY = hinhGopY;
        this.tenGopY = tenGopY;
    }

    public int getHinhGopY() {
        return hinhGopY;
    }

    public void setHinhGopY(int hinhGopY) {
        this.hinhGopY = hinhGopY;
    }

    public String getTenGopY() {
        return tenGopY;
    }

    public void setTenGopY(String tenGopY) {
        this.tenGopY = tenGopY;
    }

    public int getIdGopY() {
        return idGopY;
    }

    public void setIdGopY(int idGopY) {
        this.idGopY = idGopY;
    }
}
