package com.example.legia.mobileweb.Database.Firebase.DAO;

import com.example.legia.mobileweb.Database.Database;
import com.example.legia.mobileweb.Database.Firebase.DTO.preOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class preOrderDAO {
    public static int insertPreOder(preOrder userOrder){
        int insert = 0;
        try {
            Connection db = Database.connect();
            String sql = "INSERT INTO `don_hang_dat_hang` (`ho_user`, `ten_user`, `sdt`, `email`) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setString(1, userOrder.getHo_user());
            pst.setString(2, userOrder.getTen_user());
            pst.setInt(3, userOrder.getSdt());
            pst.setString(4, userOrder.getEmail());

            insert = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insert;
    }
}
