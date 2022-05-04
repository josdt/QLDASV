import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url="jdbc:sqlserver://localhost:1433;databaseName=QLDASV";
            String user="sa";
            String password="123";
            con= DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Ket noi thanh cong");
        } 
        return con;
    }
    public static void disCon(Connection con){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }
    } 
}


