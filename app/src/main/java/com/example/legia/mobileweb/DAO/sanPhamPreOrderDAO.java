package com.example.legia.mobileweb.DAO;

import com.example.legia.mobileweb.DTO.sanPhamPreOrder;
import com.example.legia.mobileweb.Database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class sanPhamPreOrderDAO {
    public static List<sanPhamPreOrder> dsSanPhamPreOrder(){
        List<sanPhamPreOrder> dsSanPhamPreOrder = new ArrayList<>();
        try {
            Connection db = Database.connect();
            String sql = "select*from san_pham_dat_hang";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                sanPhamPreOrder sanPhamPreOrder = new sanPhamPreOrder();
                sanPhamPreOrder.setIdSanPham(rs.getInt("idsan_pham_dat_hang"));
                sanPhamPreOrder.setTen_san_pham(rs.getString("ten_san_pham"));
                sanPhamPreOrder.setHang_san_xuat(rs.getString("hang_san_xuat"));
                sanPhamPreOrder.setGia_san_pham(rs.getInt("gia_san_pham"));
                sanPhamPreOrder.setHinh_san_pham(rs.getBlob("hinh_san_pham"));
                sanPhamPreOrder.setThong_tin(rs.getString("thong_tin_co_ban"));
                sanPhamPreOrder.setNgay_du_kien(rs.getTimestamp("ngay_du_kien"));
                dsSanPhamPreOrder.add(sanPhamPreOrder);
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSanPhamPreOrder;
    }
}
