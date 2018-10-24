package com.example.legia.mobileweb.DTO;

public class hangSpinner {
    private int maHang;
    private String Hang;

    public hangSpinner(int maHang, String hang) {
        this.maHang = maHang;
        Hang = hang;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getHang() {
        return Hang;
    }

    public void setHang(String hang) {
        Hang = hang;
    }
}
