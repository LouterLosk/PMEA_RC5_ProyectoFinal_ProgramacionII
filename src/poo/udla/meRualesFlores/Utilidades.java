package poo.udla.meRualesFlores;

import java.sql.Connection;
import java.sql.DriverManager;

public class Utilidades {
    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/sistema_ventas";
        String user = "root";
        String passwd = "sasa";

        Connection conn = null;

        try{
                conn = DriverManager.getConnection(url, user, passwd);
                return conn;
        } catch (Exception ex){
                    ex.printStackTrace();
        }
        return null;
    }
}

