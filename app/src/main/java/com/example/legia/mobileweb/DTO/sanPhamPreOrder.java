package com.example.legia.mobileweb.DTO;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class sanPhamPreOrder {
    private int idSanPham;
    private String ten_san_pham;
    private String hang_san_xuat;
    private int gia_san_pham;
    private Blob hinh_san_pham;
    private String thong_tin;
    private Timestamp ngay_du_kien;

    public sanPhamPreOrder() {
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTen_san_pham() {
        return ten_san_pham;
    }

    public void setTen_san_pham(String ten_san_pham) {
        this.ten_san_pham = ten_san_pham;
    }

    public int getGia_san_pham() {
        return gia_san_pham;
    }

    public void setGia_san_pham(int gia_san_pham) {
        this.gia_san_pham = gia_san_pham;
    }

    public Blob getHinh_san_pham() {
        return hinh_san_pham;
    }

    public void setHinh_san_pham(Blob hinh_san_pham) {
        this.hinh_san_pham = hinh_san_pham;
    }

    public String getThong_tin() {
        return thong_tin;
    }

    public void setThong_tin(String thong_tin) {
        this.thong_tin = thong_tin;
    }

    public Timestamp getNgay_du_kien() {
        return ngay_du_kien;
    }

    public void setNgay_du_kien(Timestamp ngay_du_kien) {
        this.ngay_du_kien = ngay_du_kien;
    }

    public String getHang_san_xuat() {
        return hang_san_xuat;
    }

    public void setHang_san_xuat(String hang_san_xuat) {
        this.hang_san_xuat = hang_san_xuat;
    }
}
