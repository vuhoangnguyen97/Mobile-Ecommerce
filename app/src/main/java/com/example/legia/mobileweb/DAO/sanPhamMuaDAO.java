package com.example.legia.mobileweb.DAO;

import com.example.legia.mobileweb.DTO.hoaDon;
import com.example.legia.mobileweb.DTO.themSanPhamMua;
import com.example.legia.mobileweb.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class sanPhamMuaDAO {
    public static int themHoaDon(hoaDon hd) {
        int status =0;
        String sql = "insert into gio_hang(iduser, email, ho_user, ten_user, sdt, diaChi, quan, phuong, chi_tiet) values(?,?,?,?,?,?,?,?,?)";
        try {
            Connection db = Database.connect();
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, hd.getId_user());
            pst.setString(2, hd.getEmail());
            pst.setString(3, hd.getHo_user());
            pst.setString(4, hd.getTen_user());
            pst.setInt(5, hd.getSdt());
            pst.setString(6, hd.getDiaChi());;
            pst.setString(7, hd.getQuan());
            pst.setString(8, hd.getPhuong());
            pst.setString(9, hd.getChiTiet());

            status = pst.executeUpdate();
            db.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return status;
    }

    public static int themSPMua(themSanPhamMua spm) {
        int status =0;
        String sql = "insert into hthong_muaban.gio_hang_has_san_pham(idgio_hang,iduser,ma_san_pham) values(?,?,?)";
        try {
            Connection db = Database.connect();
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, spm.getIdgio_hang());
            pst.setInt(2, spm.getIduser());
            pst.setInt(3, spm.getMa_san_pham());

            status = pst.executeUpdate();
            db.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return status;
    }
}
