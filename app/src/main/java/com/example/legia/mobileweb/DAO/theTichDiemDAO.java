package com.example.legia.mobileweb.DAO;

import com.example.legia.mobileweb.DTO.theKhachHang;
import com.example.legia.mobileweb.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class theTichDiemDAO {
    public static int tichDiem(int id_the_tich_diem, int diem){
        int status =0;
        try {
            String sql = "UPDATE the_tich_diem SET `diem_tich_luy`= ? WHERE `id_the_tich_diem`=?;";
            Connection db = Database.connect();
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, diem);
            pst.setInt(2, id_the_tich_diem);
            status = pst.executeUpdate();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int nangCapThe(int id_the_tich_diem, int id_loai_the){
        int status = 0;
        Connection db = Database.connect();
        try {
            String sql = "UPDATE the_tich_diem SET `id_loai_the`= ? WHERE `id_the_tich_diem`=?;";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, id_loai_the);
            pst.setInt(2, id_the_tich_diem);
            status = pst.executeUpdate();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static theKhachHang theKhachHang(int id_the_tich_diem){
        theKhachHang theKhachHang = null;
        try {
            Connection db = Database.connect();
            PreparedStatement pst = db.prepareStatement("SELECT*FROM the_tich_diem WHERE id_the_tich_diem = ?");
            pst.setInt(1, id_the_tich_diem);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                theKhachHang =new theKhachHang();
                theKhachHang.setMaThe(rs.getInt("id_the_tich_diem"));
                theKhachHang.setDiem(rs.getInt("diem_tich_luy"));
                theKhachHang.setMaLoaiThe(rs.getInt("id_loai_the"));
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theKhachHang;
    }
}
