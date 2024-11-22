package db;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thành công!");
            }
            else {
            	System.out.println("looi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
