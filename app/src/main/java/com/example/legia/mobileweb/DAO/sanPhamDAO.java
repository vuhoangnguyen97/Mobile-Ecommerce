package com.example.legia.mobileweb.DAO;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.legia.mobileweb.DTO.sanPham;
import com.example.legia.mobileweb.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class sanPhamDAO{
    public static List<sanPham> DocTatCa(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM san_pham ORDER by rand()");
            dsSanPham = new ArrayList<>();

            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }


        return dsSanPham;

    }


    public static List<sanPham> danhSachSoSanh(int maSanPhamChinh){
        List<sanPham> dsSanPhamSoSanh = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select*from hthong_muaban.san_pham where not ma_san_pham = '"+maSanPhamChinh+"'");
            dsSanPhamSoSanh = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));

                dsSanPhamSoSanh.add(sp);
            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return dsSanPhamSoSanh;
    }

    public static List<sanPham> timTheoTen(String tenSanPhamCanTim){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select*from san_pham where ten_san_pham like '%"+tenSanPhamCanTim.replaceAll("[^\\w\\s]","")+"%'");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);
            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGiaDuoi1Trieu(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham <1000000");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGiaDuoi1TrieuMain(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham <500000 limit 0,4");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGia1TrieuDen3Trieu(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham >=1000000 and gia_san_pham <3000000 ");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGia1TrieuDen3TrieuMain(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham >=1000000 and gia_san_pham <3000000 limit 0,4");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGia3TrieuDen7Trieu(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham >=3000000 and gia_san_pham <7000000 ");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGia7TrieuDen10Trieu(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham >=7000000 and gia_san_pham <10000000 ");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);
            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timTheoGiaTren10Trieu(){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from san_pham where gia_san_pham >=10000000");
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return dsSanPham;

    }

    public static List<sanPham> timKiemTheoHang(String hang_sxuat){
        List<sanPham> dsSanPham = null;
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();

            ResultSet rs = stm.executeQuery("select * from san_pham where hang_san_xuat like '%"+hang_sxuat.replaceAll("[^\\w\\s]","")+"%' order by RAND() " );
            dsSanPham = new ArrayList<>();
            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dsSanPham.add(sp);

            }
            db.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return dsSanPham;
    }

    public static List<sanPham> SanPhamMoi(int top){
        List<sanPham> dssp_new = new ArrayList<sanPham>();
        Connection db= Database.connect();
        String sql = "SELECT * FROM hthong_muaban.san_pham order by ma_san_pham asc limit 0,"+top;
        Statement stm;

        try {
            stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dssp_new.add(sp);

            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dssp_new;
    }

    public static List<sanPham> SanPhamBottom(int bottom){
        List<sanPham> dssp_bottom = new ArrayList<sanPham>();
        Connection db= Database.connect();
        String sql = "SELECT * FROM hthong_muaban.san_pham order by ma_san_pham desc limit 0,"+bottom;
        Statement stm;

        try {
            stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while(rs.next()){
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dssp_bottom.add(sp);

            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dssp_bottom;
    }

    public static sanPham docTheoID(int id) {
        sanPham sp = null;
        Connection db = Database.connect();
        String sql = "SELECT * FROM san_pham WHERE ma_san_pham =" + id;
        Statement stm;

        try {
            stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while(rs.next()) {
                sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sp;
    }

    public static int themMoi(sanPham sanPhamMoi) {
        int status = 0;

        String sql = "INSERT INTO `hthong_muaban`.`san_pham` (ten_san_pham, hang_san_xuat, gia_san_pham, tinh_Trang, hinh_anh_mo_phong, camera_truoc, camera_sau, dung_luong_pin, tinh_nang, bao_mat, mau_sau) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        Connection db = Database.connect();
        try {

            PreparedStatement pst = db.prepareStatement(sql);
            pst.setString(1, sanPhamMoi.getTenSanPham());
            pst.setString(2, sanPhamMoi.getHangSanXuat());
            pst.setInt(3, sanPhamMoi.getGiaSanPham());
            pst.setString(4, sanPhamMoi.getTinhTrang());
            pst.setBlob(5, sanPhamMoi.getHinh_dai_dien());
            pst.setString(6, sanPhamMoi.getCamera_truoc());
            pst.setString(7, sanPhamMoi.getCamera_sau());
            pst.setString(8, sanPhamMoi.getDung_luong_pin());
            pst.setString(9, sanPhamMoi.getTinh_nang());
            pst.setString(10, sanPhamMoi.getBao_mat());
            pst.setString(11, sanPhamMoi.getMau_sac());

            status = pst.executeUpdate();
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return status;
    }

    public static List<sanPham> timTheoDungLuongPin(int dungLuongPin){
        List<sanPham> dssp = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM hthong_muaban.san_pham where dung_luong_pin >= '"+dungLuongPin+"'");

            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dssp.add(sp);
            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return dssp;
    }

    public static List<sanPham> timTheoMauSac(String mauSac){
        List<sanPham> dssp = new ArrayList<sanPham>();

        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select*from san_pham sp where sp.mau_sac like '%"+mauSac+"%' ");

            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dssp.add(sp);
            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dssp;
    }

    // nhu cầu
    public static List<sanPham> dssp_nhucau(String nhucau){
        List<sanPham> dssp = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select*from hthong_muaban.san_pham where tinh_nang like '%"+nhucau+"%'");

            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dssp.add(sp);

            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dssp;
    }

    public static List<sanPham> phuKien(String phuKien){
        List<sanPham> dspk = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select*from hthong_muaban.san_pham where ten_san_pham like '%"+phuKien+"%'");

            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                dspk.add(sp);

            }
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dspk;
    }

    public static List<sanPham> timTheoHangGiaDuoi1Trieu(String brand){
        List<sanPham> listDuoi1Trieu = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            String sql = "SELECT * FROM hthong_muaban.san_pham where hang_san_xuat like N'%"+brand+"%' and gia_san_pham <=1000000;";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                listDuoi1Trieu.add(sp);

            }
            db.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listDuoi1Trieu;
    }

    public static List<sanPham> timTheoHangGia1Den3Trieu(String brand){
        List<sanPham> list1Den3Trieu = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            String sql = "SELECT * FROM hthong_muaban.san_pham where hang_san_xuat like N'%"+brand+"%' and gia_san_pham >=1000000 and gia_san_pham <= 3000000;";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                list1Den3Trieu.add(sp);

            }
            db.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list1Den3Trieu;
    }

    public static List<sanPham> timTheoHangGia3Den7rieu(String brand){
        List<sanPham> list3Den7Trieu = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            String sql = "SELECT * FROM hthong_muaban.san_pham where hang_san_xuat like N'%"+brand+"%' and gia_san_pham >=3000000 and gia_san_pham <= 7000000;";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                list3Den7Trieu.add(sp);

            }
            db.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list3Den7Trieu;
    }

    public static List<sanPham> timTheoHangGia7Den10rieu(String brand){
        List<sanPham> list7Den10Trieu = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            String sql = "SELECT * FROM hthong_muaban.san_pham where hang_san_xuat like N'%"+brand+"%' and gia_san_pham >=7000000 and gia_san_pham <= 10000000;";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                list7Den10Trieu.add(sp);

            }
            db.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list7Den10Trieu;
    }

    public static List<sanPham> timTheoHangGiaTren10Trieu(String brand){
        List<sanPham> listTren10Trieu = new ArrayList<sanPham>();
        try {
            Connection db = Database.connect();
            String sql = "SELECT * FROM hthong_muaban.san_pham where hang_san_xuat like N'%"+brand+"%' and gia_san_pham >=10000000;";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                sanPham sp = new sanPham();
                sp.setMa_san_pham(rs.getInt("ma_san_pham"));
                sp.setTenSanPham(rs.getString("ten_san_pham"));
                sp.setHangSanXuat(rs.getString("hang_san_xuat"));
                sp.setGiaSanPham(rs.getInt("gia_san_pham"));
                sp.setTinhTrang(rs.getString("tinh_trang"));
                sp.setHinh_dai_dien(rs.getBlob("hinh_anh_mo_phong"));
                sp.setCamera_truoc(rs.getString("camera_truoc"));
                sp.setCamera_sau(rs.getString("camera_sau"));
                sp.setDung_luong_pin(rs.getString("dung_luong_pin"));
                sp.setTinh_nang(rs.getString("tinh_nang"));
                sp.setBao_mat(rs.getString("bao_mat"));
                sp.setMau_sac(rs.getString("mau_sac"));
                listTren10Trieu.add(sp);

            }
            db.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return listTren10Trieu;
    }

    public static int getLastCount(int id) {
        int count = 0;
        try {
            Connection db = Database.connect();
            String sqlGetLast = "SELECT * FROM san_pham where ma_san_pham = " + id;
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sqlGetLast);
            while (rs.next()) {
                count = rs.getInt("soLuotMua");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

        public static int updateSoLuotMua(int id){
        int update = 0;
        try {
            Connection db = Database.connect();
            String sqlUpdate = "UPDATE san_pham SET soLuotMua= ?  WHERE ma_san_pham = ? ;";

            int updateCount = getLastCount(id)+1;
            PreparedStatement  pst = db.prepareStatement(sqlUpdate);
            pst.setInt(1, updateCount);
            pst.setInt(2, id);

            update = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update;
    }


}
