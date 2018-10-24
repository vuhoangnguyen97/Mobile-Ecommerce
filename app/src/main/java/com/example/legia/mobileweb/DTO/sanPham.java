package com.example.legia.mobileweb.DTO;

import java.io.Serializable;
import java.sql.Blob;

public class sanPham implements Serializable {

    private static final long serialVersionUID = 1L;
    private int ma_san_pham;
    private String tenSanPham;
    private String hangSanXuat;
    private int giaSanPham;
    private String tinhTrang;
    private Blob hinh_dai_dien;
    private String camera_truoc;
    private String camera_sau;
    private String dung_luong_pin;
    private String tinh_nang;
    private String bao_mat;
    private String mau_sac;

    public sanPham() {
        super();
    }

    public int getMa_san_pham() {
        return ma_san_pham;
    }

    public void setMa_san_pham(int ma_san_pham) {
        this.ma_san_pham = ma_san_pham;
    }





    public String getMau_sac() {
        return mau_sac;
    }

    public void setMau_sac(String mau_sac) {
        this.mau_sac = mau_sac;
    }

    public String getCamera_truoc() {
        return camera_truoc;
    }


    public void setCamera_truoc(String camera_truoc) {
        this.camera_truoc = camera_truoc;
    }


    public String getCamera_sau() {
        return camera_sau;
    }


    public void setCamera_sau(String camera_sau) {
        this.camera_sau = camera_sau;
    }


    public String getDung_luong_pin() {
        return dung_luong_pin;
    }


    public void setDung_luong_pin(String dung_luong_pin) {
        this.dung_luong_pin = dung_luong_pin;
    }


    public String getTinh_nang() {
        return tinh_nang;
    }

    public void setTinh_nang(String tinh_nang) {
        this.tinh_nang = tinh_nang;
    }


    public String getBao_mat() {
        return bao_mat;
    }


    public void setBao_mat(String bao_mat) {
        this.bao_mat = bao_mat;
    }


    public String getHangSanXuat() {
        return hangSanXuat;
    }
    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }
    public String getTenSanPham() {
        return tenSanPham;
    }
    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
    public int getGiaSanPham() {
        return giaSanPham;
    }
    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }
    public String getTinhTrang() {
        return tinhTrang;
    }
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Blob getHinh_dai_dien() {
        return hinh_dai_dien;
    }
    public void setHinh_dai_dien(Blob hinh_dai_dien) {
        this.hinh_dai_dien = hinh_dai_dien;
    }
}