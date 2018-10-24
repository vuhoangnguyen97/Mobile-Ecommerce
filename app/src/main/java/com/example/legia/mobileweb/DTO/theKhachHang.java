package com.example.legia.mobileweb.DTO;

public class theKhachHang {
    private int maThe;
    private int diem;
    private int maLoaiThe;

    public theKhachHang() {
    }

    public theKhachHang(int maThe, int diem, int maLoaiThe) {
        this.maThe = maThe;
        this.diem = diem;
        this.maLoaiThe = maLoaiThe;
    }

    public int getMaThe() {
        return maThe;
    }

    public void setMaThe(int maThe) {
        this.maThe = maThe;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public int getMaLoaiThe() {
        return maLoaiThe;
    }

    public void setMaLoaiThe(int maLoaiThe) {
        this.maLoaiThe = maLoaiThe;
    }
}
