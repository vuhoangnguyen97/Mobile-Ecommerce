package com.example.legia.mobileweb.DAO;

import com.example.legia.mobileweb.DTO.userPreOrder;
import com.example.legia.mobileweb.Database.Database;
import com.example.legia.mobileweb.Encryption.encrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userPreOrderDAO {

    public static int preOrder(userPreOrder userPreOder){
        int preorder = 0;
        try {
            Connection db = Database.connect();
            String sql = "INSERT INTO `don_hang_dat_hang` (`ho_user`, `ten_user`, `sdt`, `email`) " +
                    "VALUES (?, ?, ?, ?);";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setString(1, userPreOder.getHo_user());
            pst.setString(2, userPreOder.getHo_user());
            pst.setInt(3, userPreOder.getSdt());
            pst.setString(4, userPreOder.getEmail());

            preorder = pst.executeUpdate();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preorder;
    }

    public static userPreOrder checkUser(int sdt){
        userPreOrder userPreOrder = null;
        try {
            Connection db = Database.connect();
            String sql = "select*from `don_hang_dat_hang` WHERE sdt = ?";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setInt(1, sdt);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                userPreOrder = new userPreOrder();
                userPreOrder.setHo_user(rs.getString("ho_user"));
                userPreOrder.setTen_user(rs.getString("ten_user"));
                userPreOrder.setSdt(rs.getInt("sdt"));
                userPreOrder.setEmail(rs.getString("email"));
            }
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userPreOrder;
    }
}
