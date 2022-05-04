/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.jndi.ldap.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ThaoTacGV {
    public static ArrayList<chamdiemHD> getTableChamDHD(){
        ArrayList<chamdiemHD> list =new ArrayList();
        java.sql.Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement("select MADOAN, MASV, MAGVHD, DIEMHD from CT_DOAN where CT_DOAN.MAGVHD=?");
                preparedStatement.setString(1, GiangVien_Form.ma);
                ResultSet resultSet= preparedStatement.executeQuery();
                while(resultSet.next()){
                    list.add(new chamdiemHD(resultSet.getString("MADOAN"),resultSet.getString("MASV"),resultSet.getString("MAGVHD"),resultSet.getFloat(4)));
                }
                return list;
            } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
            }

        return null;
    }
    public static ArrayList<chamdiemPB> getTableChamDPB(){
        ArrayList<chamdiemPB> list =new ArrayList();
        java.sql.Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement;
                try {
                preparedStatement = connection.prepareStatement("select MADOAN, MASV, MAGVPB, DIEMPB from CT_DOAN where CT_DOAN.MAGVPB=?");
                preparedStatement.setString(1, GiangVien_Form.ma);
                ResultSet resultSet= preparedStatement.executeQuery();
                while(resultSet.next()){
                    list.add(new chamdiemPB(resultSet.getString("MADOAN"),resultSet.getString("MASV"),resultSet.getString("MAGVPB"),resultSet.getFloat(4)));
                }
                return list;
            } catch (SQLException ex) {
                Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    public static ArrayList<chamdiemTB> getTableChamDiemTB(){
        ArrayList<chamdiemTB> list =new ArrayList();
        java.sql.Connection connection = DBConnection.getConnection();

            PreparedStatement preparedStatement;
                try {
                preparedStatement = connection.prepareStatement("select ctda.MADOAN, ctda.MASV, ctda.MATIEUBAN, c.MAGV, c.DIEMTIEUBAN from CT_DOAN ctda, CHAM c where ctda.MADOAN=c.MADOAN and c.MAGV=?");
                preparedStatement.setString(1, GiangVien_Form.ma);
                ResultSet resultSet= preparedStatement.executeQuery();
                while(resultSet.next()){
                    list.add(new chamdiemTB(resultSet.getString("MADOAN"),resultSet.getString("MASV"), resultSet.getString("MATIEUBAN"), resultSet.getString("MAGV"),resultSet.getFloat(5)));
                }
                return list;
            } catch (SQLException ex) {
                Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
    }

    public static void suaDiemHD(chamdiemHD cd){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("update CT_DOAN set CT_DOAN.DIEMHD=? where CT_DOAN.MADOAN= ?");
            preparedStatement.setFloat(1, cd.DiemHD);
            preparedStatement.setString(2, cd.MaDA );
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void suaDiemPB(chamdiemPB cd){
        java.sql.Connection connection=DBConnection.getConnection();
        
        try {

            PreparedStatement preparedStatement= connection.prepareStatement("update CT_DOAN set CT_DOAN.DIEMPB=? where CT_DOAN.MADOAN= ? ");
            preparedStatement.setFloat(1, cd.DiemPB);
            preparedStatement.setString(2, cd.MaDA );
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void suaDiemTB(chamdiemTB cd){
        java.sql.Connection connection=DBConnection.getConnection();
        try {

            PreparedStatement preparedStatement= connection.prepareStatement("update CHAM set CHAM.DIEMTIEUBAN=? where CHAM.MADOAN=? and CHAM.MAGV=?  ");
            preparedStatement.setFloat(1, cd.DiemTieuBan);
            preparedStatement.setString(2, cd.MaDA );
            preparedStatement.setString(3, GiangVien_Form.ma );
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Boolean ktMaDoAn(String MaDA){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from DOAN where DOAN.MADOAN=?");
            preparedStatement.setString(1,MaDA );
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public static Boolean ktMaSV(String MaSV){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from SINHVIEN where SINHVIEN.MASV=?");
            preparedStatement.setString(1,MaSV );
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    public static Boolean ktDoAnMaSV(String MaDA){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from CT_DOAN where CT_DOAN.MADOAN=?");
            preparedStatement.setString(1,MaDA );
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
        
    }

    public static Float layDHD(String maDA){
        java.sql.Connection connection=DBConnection.getConnection();
            Float diemkt = null;
        try {

            PreparedStatement preparedStatement=connection.prepareStatement("select DIEMHD from CT_DOAN where CT_DOAN.MADOAN=?");
            preparedStatement.setString(1,maDA );
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                diemkt= resultSet.getFloat("DIEMHD");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }

        return diemkt;
    }
    public static Boolean ktTTDiemHD(Float DiemHD){
       if(DiemHD>0){
           return true;
       }
       return false;

    }
    public static Boolean ktDKDiemHD(Float DiemHD){
       
           if(DiemHD>5.0){
               return true;
           }
       
        return false;
    }

    public static Float layDPB(String maDA){
        java.sql.Connection connection=DBConnection.getConnection();
            Float diemkt = null;
        try {

            PreparedStatement preparedStatement=connection.prepareStatement("select DIEMPB from CT_DOAN where CT_DOAN.MADOAN=?");
            preparedStatement.setString(1,maDA );
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                diemkt= resultSet.getFloat("DIEMPB");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }

        return diemkt;
    }
    public static Boolean ktTTDiemPB(Float DiemPB){
       if(DiemPB>0){
           return true;
       }
       return false;
    }
    public static Boolean ktDKDiemPB(Float DiemPB){
       
           if(DiemPB>5.0){
               return true;
           }
       
        return false;
    }


    public static ArrayList<doAn> getTableviewDoAn(){
        ArrayList<doAn> list =new ArrayList();
        java.sql.Connection connection=DBConnection.getConnection();
        Statement statement;
        try {
            statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery("select * from DOAN ");
            while(resultSet.next()){
                list.add(new doAn(resultSet.getString("MADOAN"), resultSet.getString("TENDOAN")));
 
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<phanDoAn> getTablephanDoAn(){
        ArrayList<phanDoAn> list =new ArrayList();
        java.sql.Connection connection=DBConnection.getConnection();

            PreparedStatement preparedStatement;
                try {
                preparedStatement = connection.prepareStatement("select MASV,MAGVHD,MADOAN from CT_DOAN where CT_DOAN.MAGVHD=?");
                preparedStatement.setString(1, GiangVien_Form.ma);
                ResultSet resultSet= preparedStatement.executeQuery();
                while(resultSet.next()){
                    list.add(new phanDoAn(resultSet.getString("MASV"),resultSet.getString("MAGVHD"), resultSet.getString("MADOAN")));
                }
                return list;
            } catch (SQLException ex) {
                Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
    }
    public static Boolean ktNullMaDoAn(String maSV){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from CT_DOAN where CT_DOAN.MASV=? AND CT_DOAN.MADOAN IS NULL");
            preparedStatement.setString(1, maSV);
            ResultSet resultSet= preparedStatement.executeQuery();
            while(resultSet.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static String getMadoanCham(String maSv){
        java.sql.Connection connection=DBConnection.getConnection();
          String maDoAn = null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select MADOAN from CT_DOAN where CT_DOAN.MASV=?");
            preparedStatement.setString(1, maSv);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                maDoAn= resultSet.getString("MADOAN");
            }
            }
        catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maDoAn ;
    }
    public static void xoaCham(String maDoAn){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("delete from CHAM where CHAM.MADOAN=?");
            preparedStatement.setString(1, maDoAn);
            preparedStatement.executeUpdate();
                    } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void phanDoAn(phanDoAn pda){
        java.sql.Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("update CT_DOAN set CT_DOAN.MADOAN=? where CT_DOAN.MASV=? ");
            preparedStatement.setString(1, pda.MaDA);
            preparedStatement.setString(2, pda.MaSV);
//            PreparedStatement preparedStatement1=connection.prepareStatement("INSERT INTO CHAM (MAGV,MADOAN)\n" +
//                                                                            "SELECT CT_TIEUBAN.MAGV, CT_DOAN.MADOAN \n" +
//                                                                            "FROM CT_TIEUBAN, CT_DOAN, (select MATIEUBAN FROM CT_DOAN WHERE MADOAN=?) AS A \n" +
//                                                                            "WHERE CT_TIEUBAN.MATIEUBAN=A.MATIEUBAN AND CT_DOAN.MADOAN=? ");
//            preparedStatement1.setString(1, pda.MaDA);
//            preparedStatement1.setString(2, pda.MaDA);
            preparedStatement.executeUpdate();
//            preparedStatement1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
