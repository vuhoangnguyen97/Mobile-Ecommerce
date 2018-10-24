package com.example.legia.mobileweb.DTO;

public class loaiThe {
    private int maLoaiThe;
    private String loaiThe;

    public loaiThe(int maLoaiThe, String loaiThe) {
        this.maLoaiThe = maLoaiThe;
        this.loaiThe = loaiThe;
    }

    public int getMaLoaiThe() {
        return maLoaiThe;
    }

    public void setMaLoaiThe(int maLoaiThe) {
        this.maLoaiThe = maLoaiThe;
    }

    public String getLoaiThe() {
        return loaiThe;
    }

    public void setLoaiThe(String loaiThe) {
        this.loaiThe = loaiThe;
    }
}
