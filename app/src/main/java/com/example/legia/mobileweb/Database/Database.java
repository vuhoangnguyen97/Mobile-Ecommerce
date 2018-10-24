package com.example.legia.mobileweb.Database;
import java.sql.Connection; // import thư viện JDBC.
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // khai báo phương thức kết nối csdl
    public static Connection connect(){
        Connection db = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            /************************************************
            *    server csdl host thật: 112.213.89.144:3306 *
			*    username: doAn2018                         *
			*    password: DoAn2018                         *
            *************************************************/
            String url = "jdbc:mysql://112.213.89.144:3306/hthong_muaban?useUnicode=true&characterEncoding=UTF-8";
            db = DriverManager.getConnection(url, "doAn2018", "DoAn2018");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return db;
    }
}
