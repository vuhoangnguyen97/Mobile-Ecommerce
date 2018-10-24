package com.example.legia.mobileweb.DAO;

import com.example.legia.mobileweb.DTO.hoaDon;
import com.example.legia.mobileweb.Database.Database;
import com.example.legia.mobileweb.Encryption.encrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class hoaDonDAO {
    public static int themHoaDon(hoaDon hd) {
        int status =0;
        String sql = "insert into gio_hang(iduser, email, ho_user, ten_user, sdt, diaChi, quan, phuong, chi_tiet, hinh_thuc_thanh_toan) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection db = Database.connect();
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, hd.getId_user());
            pst.setString(2, hd.getEmail());
            pst.setString(3, hd.getHo_user());
            pst.setString(4, hd.getTen_user());
            pst.setInt(5, hd.getSdt());
            pst.setString(6, hd.getDiaChi());
            pst.setString(7, hd.getQuan());
            pst.setString(8, hd.getPhuong());
            pst.setString(9, hd.getChiTiet());
            pst.setString(10, hd.getHinhThucThanhToan());

            status = pst.executeUpdate();
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return status;
    }

    public static List<hoaDon> readBill(int iduser){
        List<hoaDon> dsHoaDon = new ArrayList<>();
        try {
            Connection db = Database.connect();
                String sql = "SELECT*FROM gio_hang WHERE iduser = ? order by idgio_hang desc";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, iduser);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                hoaDon hd = new hoaDon();
                hd.setId_giohang(rs.getInt("idgio_hang"));
                hd.setChiTiet(rs.getString("chi_tiet"));
                dsHoaDon.add(hd);
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public static int lastID(){
        int id = 0;
        try {
            Connection db = Database.connect();
            String sql = "SELECT idgio_hang FROM `gio_hang` ORDER BY idgio_hang DESC LIMIT 1";
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                id = rs.getInt("idgio_hang");
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
