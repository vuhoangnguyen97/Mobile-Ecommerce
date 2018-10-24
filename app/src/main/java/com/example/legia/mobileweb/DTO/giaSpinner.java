package com.example.legia.mobileweb.DTO;

public class giaSpinner {
    private int maGia;
    private String gia;

    public giaSpinner(int maGia, String gia) {
        this.maGia = maGia;
        this.gia = gia;
    }

    public int getMaGia() {
        return maGia;
    }

    public void setMaGia(int maGia) {
        this.maGia = maGia;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
