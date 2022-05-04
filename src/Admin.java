
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Acer
 */
public class Admin extends javax.swing.JFrame {

    /**
     * Creates new form Admin
     */
    public Admin() {
        initComponents();
        this.setTitle("Quản lý sinh viên - Quyền ADMIN");
        this.setLocationRelativeTo(this);
        tab_Center.setSelectedIndex(0);
        hienThiBangQLND();
        hienThiBangQLSV();
        hienThiBangQLGV();
        hienThiBangQLHD();
//        hienThiBangDoAn();
        hienThiBangTieuBan();
        hienThiBangGVConLai();
        timKiemSV();
        //loadComboBoxLop();
        //loadTieuBan();
        loadPhanCong();

//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void loadMaComboBoxLop() {
        Connection ketNoi = DBConnection.getConnection();
        String sql = "SELECT distinct LOP FROM SINHVIEN";

        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jComboBox_Lop.addItem(rs.getString("LOP").toUpperCase());

            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadTenComboBoxLop() {
        Connection ketNoi = DBConnection.getConnection();
        String sql = "SELECT distinct LOP FROM SINHVIEN";

        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jComboBox_ThongkeDiemTheoLop.addItem(rs.getString("LOP").toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public double gioi() {
        String lop = (String) jComboBox_ThongkeDiemTheoLop.getSelectedItem();
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select count(diemTB) FROM( \n"
                + "select ((diemhd+diempb+diemtieuban)/3) as diemTB from(\n"
                + "select  diemhd, diempb ,round(avg(diemtieuban),2)as diemtieuban  from cham,(\n"
                + "select sinhvien.masv, tensv, gioitinh, a.madoan, diemhd, diempb from sinhvien, ct_doan ,(\n"
                + "select distinct madoan  from cham \n"
                + "except select distinct madoan from cham where diemtieuban<5\n"
                + "except select distinct madoan from cham where diemtieuban is null)as a\n"
                + "where sinhvien.masv= ct_doan.masv and ct_doan.madoan =a.madoan and lop=?)as b\n"
                + "where cham.madoan=b.madoan\n"
                + "group by masv, tensv, gioitinh, b.madoan, diemhd, diempb)as c )as d\n"
                + "where diemTB>=8";
        double i = 0;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1,lop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = Double.parseDouble(rs.getString(""));
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }

    public double kha() {
        String lop = (String) jComboBox_ThongkeDiemTheoLop.getSelectedItem();
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select count(diemTB) FROM( \n"
                + "select ((diemhd+diempb+diemtieuban)/3) as diemTB from(\n"
                + "select  diemhd, diempb ,round(avg(diemtieuban),2)as diemtieuban  from cham,(\n"
                + "select sinhvien.masv, tensv, gioitinh, a.madoan, diemhd, diempb from sinhvien, ct_doan ,(\n"
                + "select distinct madoan  from cham \n"
                + "except select distinct madoan from cham where diemtieuban<5\n"
                + "except select distinct madoan from cham where diemtieuban is null)as a\n"
                + "where sinhvien.masv= ct_doan.masv and ct_doan.madoan =a.madoan and lop=?)as b\n"
                + "where cham.madoan=b.madoan\n"
                + "group by masv, tensv, gioitinh, b.madoan, diemhd, diempb)as c )as d\n"
                + "where diemTB<8 and diemTB>=6.5";
        double i = 0;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            // ps.setString(1, jComboBox_ThongkeDiemTheoLop.getSelectedItem().toString());
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = Double.parseDouble(rs.getString(""));
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }

    public double trungBinh() {
        String lop = (String) jComboBox_ThongkeDiemTheoLop.getSelectedItem();
        Connection ketNoi = DBConnection.getConnection();
        String sql = " select count(diemTB) FROM( \n"
                + "select ((diemhd+diempb+diemtieuban)/3) as diemTB from(\n"
                + "select  diemhd, diempb ,round(avg(diemtieuban),2)as diemtieuban  from cham,(\n"
                + "select sinhvien.masv, tensv, gioitinh, a.madoan, diemhd, diempb from sinhvien, ct_doan ,(\n"
                + "select distinct madoan  from cham \n"
                + "except select distinct madoan from cham where diemtieuban<5\n"
                + "except select distinct madoan from cham where diemtieuban is null)as a\n"
                + "where sinhvien.masv= ct_doan.masv and ct_doan.madoan =a.madoan and lop=?)as b\n"
                + "where cham.madoan=b.madoan\n"
                + "group by masv, tensv, gioitinh, b.madoan, diemhd, diempb)as c )as d\n"
                + "where diemTB>=5 and diemTB<6.5";
        double i = 0;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = Double.parseDouble(rs.getString(""));
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }

    public double khongxet() {
        String lop = (String) jComboBox_ThongkeDiemTheoLop.getSelectedItem();
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select count (masv) from sinhvien where xetdoan=0 and lop='" + lop + "'";
        double i = 0;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            // ps.setString(1, jComboBox_ThongkeDiemTheoLop.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = Double.parseDouble(rs.getString(""));
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return i;
    }

    public double tong() {
        String lop = (String) jComboBox_ThongkeDiemTheoLop.getSelectedItem();
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select count (masv) from sinhvien where  lop='" + lop + "'";
        double i = 0;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            //ps.setString(1, jComboBox_ThongkeDiemTheoLop.getSelectedItem().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = Double.parseDouble(rs.getString(""));
            }
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException e) {
            Logger.getLogger(Admin.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        return i;

    }

    public void thongKeGV(String tentieuban) {

        Connection ketNoi = DBConnection.getConnection();
        DefaultTableModel dtm = (DefaultTableModel) jTable_GV.getModel();
        dtm.setNumRows(0);
        String sql = " select giangvien.magv, tengv from giangvien, CT_TIEUBAN, \n"
                + "(select matieuban from tieuban where tentieuban= ? )as a\n"
                + "where a.matieuban=CT_TIEUBAN.MATIEUBAN and CT_TIEUBAN.MAGV=GIANGVIEN.magv       ";

        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ps.setString(1, tentieuban);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector vt = new Vector();
                vt.add(rs.getString("magv"));
                vt.add(rs.getString("tengv"));
                dtm.addRow(vt);
            }
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException e) {
            Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void thongKeGV() {
        String tentieuban = (String) jComboBox_TieuBan.getSelectedItem();
        Connection ketNoi = DBConnection.getConnection();
        DefaultTableModel dtm = (DefaultTableModel) jTable_GV.getModel();
        dtm.setNumRows(0);
        String sql = " select giangvien.magv, tengv from giangvien, CT_TIEUBAN, \n"
                + "                (select matieuban from tieuban where tentieuban= ? )as a\n"
                + "               where a.matieuban=CT_TIEUBAN.MATIEUBAN and CT_TIEUBAN.MAGV=GIANGVIEN.MAGV ";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ps.setString(1, tentieuban);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector vt = new Vector();
                vt.add(rs.getString("magv"));
                vt.add(rs.getString("tengv"));
                dtm.addRow(vt);
            }
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException e) {
            Logger.getLogger(SinhVien.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void ThongKeSV() {
        String lop = (String) jComboBox_Lop.getSelectedItem();

        Connection ketNoi = DBConnection.getConnection();
        String sql1 = "select masv, tensv, gioitinh from sinhvien where lop=? and xetdoan=0";

        String sql21 = "select sinhvien.masv, tensv, gioitinh, madoan from sinhvien, (\n"
                + "  select masv,a.madoan, diemhd, diempb from ct_doan,(\n"
                + "  select madoan from ct_doan\n"
                + "  except select distinct madoan from cham) as a\n"
                + "  where  a.madoan= ct_doan.madoan and a.madoan is not null and diempb is null and diemhd is null) as b\n"
                + "  where lop=? and b.masv=sinhvien.masv and xetdoan=1";
        String sql22 = "select sinhvien.masv, tensv, gioitinh, madoan from sinhvien, (\n"
                + "  select masv,a.madoan, diemhd, diempb from ct_doan,(\n"
                + " select madoan from ct_doan\n"
                + "  except select distinct madoan from cham) as a\n"
                + "  where  a.madoan= ct_doan.madoan and a.madoan is not null and diempb is null and diemhd is not null) as b\n"
                + "  where lop=? and b.masv=sinhvien.masv and xetdoan=1 and diemhd>5";
        String sql23 = "select sinhvien.masv, tensv, gioitinh, madoan, diemhd from sinhvien, (\n"
                + " select masv,a.madoan, diemhd, diempb from ct_doan,(\n"
                + " select madoan from ct_doan\n"
                + " except select distinct madoan from cham) as a\n"
                + " where  a.madoan= ct_doan.madoan and a.madoan is not null and diemhd is not null) as b\n"
                + "  where lop=? and b.masv=sinhvien.masv and xetdoan=1 and b.diemhd<5  ";
        String sql24 = "select sinhvien.masv, tensv, gioitinh, madoan, diemhd, diempb from sinhvien, (\n"
                + "   select masv,a.madoan, diemhd, diempb from ct_doan,(\n"
                + "   select madoan from ct_doan\n"
                + "   except select distinct madoan from cham) as a\n"
                + "  where  a.madoan= ct_doan.madoan and a.madoan is not null and diempb is not null and diemhd is not null) as b\n"
                + "  where lop=? and b.masv=sinhvien.masv and xetdoan=1 and diemhd>5 and diempb<5";
        String sql25 = "select sinhvien.masv, tensv, gioitinh, madoan from sinhvien, (\n"
                + " select masv,a.madoan, diemhd, diempb from ct_doan,(\n"
                + " select madoan from ct_doan\n"
                + " except select distinct madoan from cham) as a\n"
                + " where  a.madoan= ct_doan.madoan and a.madoan is not null and diempb is not null and diemhd is not null) as b\n"
                + " where lop= ? and b.masv=sinhvien.masv and xetdoan=1 and diemhd>5 and diempb>5";
        String sql3 = "select sinhvien.masv, tensv, gioitinh from sinhvien , ct_doan where lop= ? and sinhvien.masv=ct_doan.masv and madoan is null and xetdoan=1";

        DefaultTableModel dtm = (DefaultTableModel) jTable_DS_SV_DOAN.getModel();
        dtm.setNumRows(0);
        Vector vt;
        int i = 1;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql1);
            ps.setString(1, lop);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add("Không Đủ Điều Kiện Làm Đồ án");

                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql3);
            ps.setString(1, lop);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add("chưa cập nhật ");
                vt.add("");
                vt.add("");

                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            System.err.println("loi madoan null");
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql21);
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add("");
                vt.add("");

                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql22);
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add("");
                vt.add("");

                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql23);
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add(Math.round((rs.getFloat("diemhd")) / 3));
                vt.add("RỚT");

                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql24);
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add(Math.round((rs.getFloat("diemhd") + rs.getFloat("diempb")) / 3));
                vt.add("Rớt");
                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql25);
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add("");
                vt.add("");

                i++;
                dtm.addRow(vt);
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

        String sql41 = "select sinhvien.masv, tensv, gioitinh, a.madoan from sinhvien, ct_doan ,(\n"
                + "select distinct madoan from cham where diemtieuban is null) as a\n"
                + "where sinhvien.masv= ct_doan.masv and ct_doan.madoan =a.madoan and lop=?";
        String sql42 = "select distinct ct_doan.masv, tensv, gioitinh, a.madoan, diemhd, diempb from sinhvien, ct_doan , cham, (\n"
                + "select distinct madoan from cham where diemtieuban<5\n"
                + "except select distinct madoan from cham where diemtieuban is null)as a\n"
                + "where sinhvien.masv= ct_doan.masv and ct_doan.madoan =a.madoan and lop=? and cham.madoan=ct_doan.madoan and cham.madoan=a.madoan";
        String sql43 = "select masv, tensv, gioitinh, b.madoan, diemhd, diempb ,round(avg(diemtieuban),2)as diemtieuban  from cham,(\n"
                + "select sinhvien.masv, tensv, gioitinh, a.madoan, diemhd, diempb from sinhvien, ct_doan ,(\n"
                + "select distinct madoan  from cham \n"
                + "except select distinct madoan from cham where diemtieuban<5\n"
                + "except select distinct madoan from cham where diemtieuban is null)as a\n"
                + "where sinhvien.masv= ct_doan.masv and ct_doan.madoan =a.madoan and lop=?)as b\n"
                + "where cham.madoan=b.madoan\n"
                + "group by masv, tensv, gioitinh, b.madoan, diemhd, diempb ";

        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql41);
            ps.setString(1, lop);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add("");
                vt.add("");
                i++;
                dtm.addRow(vt);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql42);
            ps.setString(1, lop);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add(Math.round((rs.getFloat("diemhd") + rs.getFloat("diempb")) / 3));
                vt.add("RỚT");
                i++;
                dtm.addRow(vt);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql43);
            ps.setString(1, lop);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vt = new Vector();
                vt.add(i);
                vt.add(rs.getString("masv"));
                vt.add(rs.getString("tensv"));
                vt.add(rs.getString("gioitinh"));
                vt.add(rs.getString("madoan"));
                vt.add(Math.round((rs.getFloat("diemhd") + rs.getFloat("diempb") + rs.getFloat("diemtieuban")) / 3));
                float diemTB = Math.round((rs.getFloat("diemhd") + rs.getFloat("diempb") + rs.getFloat("diemtieuban")) / 3);
                if (diemTB >= 8) {
                    vt.add("GIỎI");
                } else if (diemTB >= 6.5) {
                    vt.add("KHÁ");
                } else if (diemTB >= 5) {
                    vt.add("TRUNG BÌNH");
                } else {
                    vt.add("RỚT");
                }
                i++;
                dtm.addRow(vt);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadcbbgvhd() {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select magv from giangvien";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jcbbgvhd.addItem(rs.getString("magv").toUpperCase());

            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadcbbgvpb(String maSV) {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select magv from giangvien except select magvhd from ct_doan where ct_doan.masv='" + maSV + "'";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jcbbgvpb.addItem(rs.getString("magv").toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadMaSV() {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select * from ct_doan";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jcbbmasv.addItem(rs.getString("masv").toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadMaTieuBan0() {
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select distinct matieuban from ct_tieuban";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jcbbtb.addItem(rs.getString(1).toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadMaTieuBan1(String maSv) {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select distinct CT_TIEUBAN.MATIEUBAN from ct_tieuban, (select matieuban from ct_tieuban,(select magvhd from ct_doan where masv='" + maSv + "') AS a where ct_tieuban.magv=a.magvhd) AS b where ct_tieuban.matieuban<>b.matieuban";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jcbbtb.addItem(rs.getString(1).toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadMaTieuBan3(String maSv) {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select distinct CT_TIEUBAN.MATIEUBAN from ct_tieuban, (select matieuban from ct_tieuban,(select magvpb from ct_doan where masv='" + maSv + "') AS a where ct_tieuban.magv=a.magvpb) AS b where ct_tieuban.matieuban<>b.matieuban";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jcbbtb.addItem(rs.getString(1).toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadMaTieuBan(String maSv) {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select distinct CT_TIEUBAN.MATIEUBAN from ct_tieuban, (select matieuban from ct_tieuban,(select magvhd from ct_doan where masv='" + maSv + "') AS a where ct_tieuban.magv=a.magvhd) AS b,(select matieuban from ct_tieuban,(select magvpb from ct_doan where masv='" + maSv + "') AS c where ct_tieuban.magv=c.magvpb) AS d where ct_tieuban.matieuban<>b.matieuban and ct_tieuban.matieuban<>d.matieuban";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                jcbbtb.addItem(rs.getString(1).toUpperCase());
            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadTenTieuBan() {

        Connection ketNoi = DBConnection.getConnection();
        String sql = "select * from tieuban   ";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jComboBox_TieuBan.addItem(rs.getString(2).toUpperCase());

            }
            rs.close();
            ps.close();
            ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadPhanCong() {
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select sinhvien.masv, magvhd, magvpb, matieuban from sinhvien, ct_doan where sinhvien.masv=ct_doan.masv ";
        DefaultTableModel dtm = (DefaultTableModel) jTablephancong.getModel();
        dtm.setNumRows(0);
        Vector vt = null;
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
                vt.add(rs.getString(3));
                vt.add(rs.getString(4));
                dtm.addRow(vt);
            }

            jTablephancong.setModel(dtm);
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException e) {
            System.err.println("Loi ThongKeSV()");
        }
    }

//    public void luuphancong2(String madoan, String mahd,Double diemhd, String mapb,Double diempb, String matb) {
//        java.sql.Connection connection = DBConnection.getConnection();
//        try {
//
//            PreparedStatement preparedStatement = connection.prepareStatement("update CT_DOAN set CT_DOAN.MADOAN=?,CT_DOAN.MAGVHD=?, CT_DOAN.DIEMHD=?, CT_DOAN.MAGVPB=?,CT_DOAN.DIEMPB=?, CT_DOAN.MATIEUBAN=? where CT_DOAN.MASV=?  ");
//            preparedStatement.setString(1, madoan);
//            preparedStatement.setString(2, mahd);
//            preparedStatement.setDouble(3, diemhd);
//            preparedStatement.setString(4, mapb);
//            preparedStatement.setDouble(5, diempb);
//            preparedStatement.setString(6, matb);
//            preparedStatement.setString(7, jtfmasv.getText().toString().trim());
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void luuphanconghd() {
        java.sql.Connection connection = DBConnection.getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("update CT_DOAN set CT_DOAN.MAGVHD=? where CT_DOAN.MASV=?  ");
            preparedStatement.setString(1, jcbbgvhd.getSelectedItem().toString().trim());
            preparedStatement.setString(2, jcbbmasv.getSelectedItem().toString().trim());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void luuphancongpb() {
        java.sql.Connection connection = DBConnection.getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("update CT_DOAN set CT_DOAN.MAGVPB=? where CT_DOAN.MASV=?  ");
            preparedStatement.setString(1, jcbbgvpb.getSelectedItem().toString().trim());
            preparedStatement.setString(2, jcbbmasv.getSelectedItem().toString().trim());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void luuphancongtb() {
        java.sql.Connection connection = DBConnection.getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("update CT_DOAN set CT_DOAN.MATIEUBAN=? where CT_DOAN.MASV=?  ");
            preparedStatement.setString(1, jcbbtb.getSelectedItem().toString().trim());
            preparedStatement.setString(2, jcbbmasv.getSelectedItem().toString().trim());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String TimTieuBan(String magv) {
        Connection ketnoi = DBConnection.getConnection();
        String sql = "select matieuban from CT_TIEUBAN where magv=?";
        String matb = "";
        try {
            PreparedStatement ps = ketnoi.prepareStatement(sql);
            ps.setString(1, magv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                matb = rs.getString(1).toString();
            }
            rs.close();
            ps.close();
            ketnoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        //System.out.println(matb);
        return matb;
    }

    public void hienThiBangTieuBan() {
        try {
            String url = "jdbc:sqlserver://;databaseName=QLDASV;user=sa;password=123";
            Connection con = DriverManager.getConnection(url);
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select * from CT_TIEUBAN");
            DefaultTableModel tbModel = (DefaultTableModel) table_PhanCongTieuBan.getModel();
            tbModel.setRowCount(0);
            while (rs.next()) {
                String maTB = rs.getString("matieuban");
                //String tenDN=rs.getString("tendangnhap");
                String maGV = rs.getString("magv");
                String bang[] = {maTB, maGV};
                tbModel.addRow(bang);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

//    public void hienThiBangDoAn() {
//        try {
//            String url = "jdbc:sqlserver://;databaseName=QLDASV;user=sa;password=123";
//            Connection con = DriverManager.getConnection(url);
//            Statement ps = con.createStatement();
//            ResultSet rs = ps.executeQuery("select * from doan");
//            DefaultTableModel tbModel = (DefaultTableModel) table_DanhMucDoAn.getModel();
//            tbModel.setRowCount(0);
//            while (rs.next()) {
//                String maDA = rs.getString("madoan");
//                //String tenDN=rs.getString("tendangnhap");
//                String tenDA = rs.getString("tendoan");
//                String bang[] = {maDA, tenDA};
//                tbModel.addRow(bang);
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    }
    public void hienThiBangQLSV() {
        try {
            String url = "jdbc:sqlserver://;databaseName=QLDASV;user=sa;password=123";
            Connection con = DriverManager.getConnection(url);
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select * from sinhvien");
            DefaultTableModel tbModelSV = (DefaultTableModel) table_QuanLySinhVien.getModel();
            tbModelSV.setRowCount(0);
            while (rs.next()) {
                String maSV = rs.getString("masv");
                String tenSV = rs.getString("tensv");
                String lop = rs.getString("lop");
                String gioiTinh = rs.getString("gioitinh");
//                String xetDA= rs.getString("xetdoan");
                int xetDA = rs.getInt("xetdoan");
                String XDA = null;
                if (xetDA == 1) {
                    XDA = "ĐƯỢC XÉT";
                } else if (xetDA == 0) {
                    XDA = "KHÔNG ĐỦ ĐIỀU KIỆN";
                }
                String bangSV[] = {maSV, tenSV, lop, gioiTinh, XDA};
                tbModelSV.addRow(bangSV);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void hienThiBangQLGV() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from giangvien");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tbModelSV = (DefaultTableModel) table_QLGV.getModel();
            tbModelSV.setRowCount(0);
            while (rs.next()) {
                String maSV = rs.getString("magv");
                String tenSV = rs.getString("tengv");
                String bangSV[] = {maSV, tenSV};
                tbModelSV.addRow(bangSV);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void hienThiBangQLHD() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from HOIDONGBAOCAO");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tbModelSV = (DefaultTableModel) table_QLHD.getModel();
            tbModelSV.setRowCount(0);
            while (rs.next()) {
                String maHD = rs.getString("mahdbc");
                String tenHD = rs.getString("tenhdbc");
                String bangHD[] = {maHD, tenHD};
                tbModelSV.addRow(bangHD);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void hienThiBangQLTB() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from TIEUBAN");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tbModelSV = (DefaultTableModel) table_QLTB.getModel();
            tbModelSV.setRowCount(0);
            while (rs.next()) {
                String maTB = rs.getString("matieuban");
                String tenTB = rs.getString("tentieuban");
                String maHD = rs.getString("mahdbc");
                String bangTB[] = {maTB, tenTB, maHD};
                tbModelSV.addRow(bangTB);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void hienThiBangQLND() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from taikhoan");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tbModel = (DefaultTableModel) table_QuanLyNguoiDung.getModel();
            tbModel.setRowCount(0);
            while (rs.next()) {
                String maDN = rs.getString("madangnhap");
                String matKhau = rs.getString("matkhau");
                String phanQuyen = rs.getString("maphanquyen");
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PHANQUYEN where maphanquyen='" + phanQuyen + "'");
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    String phanQuyen1 = rs1.getString("tenphanquyen");
                    String bang[] = {maDN, matKhau, phanQuyen1};
                    tbModel.addRow(bang);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void hienThiBangGVConLai() {
        try {
//            String url="jdbc:sqlserver://;databaseName=QLDASV;user=sa;password=123";
            Connection con = DBConnection.getConnection();
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select magv from giangvien except select magv from CT_TIEUBAN");
            DefaultTableModel tbModel = (DefaultTableModel) table_PhanCongTieuBan_GV.getModel();
            tbModel.setRowCount(0);
            while (rs.next()) {
                String maGV = rs.getString("magv");
                String bang[] = {maGV};
                tbModel.addRow(bang);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        Panel_Tong = new javax.swing.JPanel();
        tab_Center = new javax.swing.JTabbedPane();
        tabHome = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tabPanel_QuanLy = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_QuanLyNguoiDung = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        tabQLND_tf_MatKhau = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tabQLND_btn_ChinhSua = new javax.swing.JButton();
        tabQLND_btn_Thoat = new javax.swing.JButton();
        tabQLND_lb_MK = new javax.swing.JLabel();
        tabQLND_lb_maDN = new javax.swing.JLabel();
        tabQLND_tf_MaDN = new javax.swing.JTextField();
        tabPanel_SinhVien = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_QuanLySinhVien = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        tabQLSV_tf_maSV = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tabQLSV_tf_tenSV = new javax.swing.JTextField();
        tabQLSV_tf_Lop = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        tabQLSV_cb_XetDA = new javax.swing.JComboBox<>();
        tabQLSV_btn_Thoat = new javax.swing.JButton();
        tabQLSV_btn_ThemMoi = new javax.swing.JButton();
        tabQLSV_btn_Sua = new javax.swing.JButton();
        tabQLSV_btn_Xoa = new javax.swing.JButton();
        tabQLSV_rdb_Nam = new javax.swing.JRadioButton();
        tabQLSV_rdb_Nu = new javax.swing.JRadioButton();
        tabPanel_GiangVien = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tabQLGV_tf_maGV = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tabQLGV_tf_tenGV = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        table_QLGV = new javax.swing.JTable();
        tabQLGV_btn_sua = new javax.swing.JButton();
        tabQLGV_btn_xoa = new javax.swing.JButton();
        tabQLGV_btn_thoat = new javax.swing.JButton();
        tabQLGV_btn_them = new javax.swing.JButton();
        tabPanel_HoiDong = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tabQLHD_tf_maHD = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        tabQLHD_tf_tenHD = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        table_QLHD = new javax.swing.JTable();
        tabQLHD_btn_sua = new javax.swing.JButton();
        tabQLHD_btn_xoa = new javax.swing.JButton();
        tabQLHD_btn_thoat = new javax.swing.JButton();
        tabQLHD_btn_them = new javax.swing.JButton();
        tabPanel_TieuBan = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        tabQLTB_tf_maTB = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        tabQLTB_tf_tenTB = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        table_QLTB = new javax.swing.JTable();
        tabQLTB_btn_sua = new javax.swing.JButton();
        tabQLTB_btn_xoa = new javax.swing.JButton();
        tabQLTB_btn_thoat = new javax.swing.JButton();
        tabQLTB_btn_them = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        tabQLTB_cb_maHDBC = new javax.swing.JComboBox<>();
        tabPanel_PhanCongSinhVien = new javax.swing.JPanel();
        dddd1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTablephancong = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jcbbgvhd = new javax.swing.JComboBox<>();
        jcbbgvpb = new javax.swing.JComboBox<>();
        jcbbtb = new javax.swing.JComboBox<>();
        tabQLSV_btn_ThemMoi2 = new javax.swing.JButton();
        tabQLSV_btn_Sua2 = new javax.swing.JButton();
        tabQLSV_btn_Thoat2 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jcbbmasv = new javax.swing.JComboBox<>();
        tabPanel_PhanGVTB = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_PhanCongTieuBan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tabPCTB_cb_MaTB = new javax.swing.JComboBox<>();
        tabPCTB_lb_GV5 = new javax.swing.JLabel();
        tabPCTB_cb_SL = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        tabPCTB_lb_GV1 = new javax.swing.JLabel();
        tabPCTB_lb_GV2 = new javax.swing.JLabel();
        tabPCTB_lb_GV3 = new javax.swing.JLabel();
        tabPCTB_lb_GV4 = new javax.swing.JLabel();
        tabPCTB_tf_GV5 = new javax.swing.JTextField();
        tabPCTB_tf_GV1 = new javax.swing.JTextField();
        tabPCTB_tf_GV2 = new javax.swing.JTextField();
        tabPCTB_tf_GV3 = new javax.swing.JTextField();
        tabPCTB_tf_GV4 = new javax.swing.JTextField();
        tabPCTB_btn_ThemMoi = new javax.swing.JButton();
        tabPCTB_btn_Sua = new javax.swing.JButton();
        tabPCTB_btn_Xoa = new javax.swing.JButton();
        tabPCTB_btn_Thoat = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        table_PhanCongTieuBan_GV = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        tabPanel_TimKiem = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_TimKiem = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        tabTimKiem_tf_maSV = new javax.swing.JTextField();
        tabPanel_ThongKeSV = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jComboBox_Lop = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable_DS_SV_DOAN = new javax.swing.JTable();
        tabPanel_ThongKeGV = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jComboBox_TieuBan = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable_GV = new javax.swing.JTable();
        tabPanel_ThongKeDiem = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jComboBox_ThongkeDiemTheoLop = new javax.swing.JComboBox<>();
        jPanel_bieudo = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuItem_DangNhap = new javax.swing.JMenuItem();
        menuItem_Quanly = new javax.swing.JMenuItem();
        menuItem_DangXuat = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItem_Exit = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        tabQLSV = new javax.swing.JMenuItem();
        tabQLGV = new javax.swing.JMenuItem();
        tabQLHD = new javax.swing.JMenuItem();
        tabQLTB = new javax.swing.JMenuItem();
        tabPCSV = new javax.swing.JMenuItem();
        tabPGVTB = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        tabTiemKiem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();

        jMenuItem1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        Panel_Tong.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Tong.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabHome.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("PHẦN MỀM QUẢN LÝ ĐỒ ÁN SINH VIÊN");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_school_house_120px.png"))); // NOI18N

        javax.swing.GroupLayout tabHomeLayout = new javax.swing.GroupLayout(tabHome);
        tabHome.setLayout(tabHomeLayout);
        tabHomeLayout.setHorizontalGroup(
            tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabHomeLayout.createSequentialGroup()
                .addContainerGap(610, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(635, 635, 635))
            .addGroup(tabHomeLayout.createSequentialGroup()
                .addGap(439, 439, 439)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabHomeLayout.setVerticalGroup(
            tabHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHomeLayout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(271, 271, 271))
        );

        tab_Center.addTab("tab7", tabHome);

        tabPanel_QuanLy.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_QuanLy.setToolTipText("");
        tabPanel_QuanLy.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("QUẢN LÝ NGƯỜI DÙNG");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        table_QuanLyNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_QuanLyNguoiDung.setForeground(new java.awt.Color(0, 153, 153));
        table_QuanLyNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đăng Nhập", "Mật Khẩu", "Phân Quyền "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_QuanLyNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_QuanLyNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_QuanLyNguoiDung);
        if (table_QuanLyNguoiDung.getColumnModel().getColumnCount() > 0) {
            table_QuanLyNguoiDung.getColumnModel().getColumn(2).setHeaderValue("Phân Quyền ");
        }

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 820, 590));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 153));
        jLabel13.setText("Mã Đăng Nhập");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, 30));

        tabQLND_tf_MatKhau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLND_tf_MatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLND_tf_MatKhauActionPerformed(evt);
            }
        });
        jPanel4.add(tabQLND_tf_MatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, 300, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 153));
        jLabel15.setText("Mật Khẩu");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 30));

        tabQLND_btn_ChinhSua.setBackground(new java.awt.Color(255, 255, 255));
        tabQLND_btn_ChinhSua.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabQLND_btn_ChinhSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabQLND_btn_ChinhSua.setText("Chỉnh sửa");
        tabQLND_btn_ChinhSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLND_btn_ChinhSuaActionPerformed(evt);
            }
        });
        jPanel4.add(tabQLND_btn_ChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 510, 160, -1));

        tabQLND_btn_Thoat.setBackground(new java.awt.Color(255, 255, 255));
        tabQLND_btn_Thoat.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabQLND_btn_Thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabQLND_btn_Thoat.setText("Thoát");
        tabQLND_btn_Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLND_btn_ThoatActionPerformed(evt);
            }
        });
        jPanel4.add(tabQLND_btn_Thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 510, 160, -1));

        tabQLND_lb_MK.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tabQLND_lb_MK.setForeground(new java.awt.Color(255, 51, 51));
        tabQLND_lb_MK.setText("Mật khẩu ít nhất 6 ký tự, không bao gồm kí tự đặc biệt");
        jPanel4.add(tabQLND_lb_MK, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 300, 30));

        tabQLND_lb_maDN.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tabQLND_lb_maDN.setForeground(new java.awt.Color(255, 51, 51));
        tabQLND_lb_maDN.setText("Mã đăng nhập không đúng");
        jPanel4.add(tabQLND_lb_maDN, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 260, 30));

        tabQLND_tf_MaDN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLND_tf_MaDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLND_tf_MaDNActionPerformed(evt);
            }
        });
        jPanel4.add(tabQLND_tf_MaDN, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 300, 40));

        tabPanel_QuanLy.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("DoiMatKhau", tabPanel_QuanLy);

        tabPanel_SinhVien.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_SinhVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 153));
        jLabel10.setText("DANH SÁCH SINH VIÊN");
        jPanel12.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

        table_QuanLySinhVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_QuanLySinhVien.setForeground(new java.awt.Color(0, 153, 153));
        table_QuanLySinhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sinh Viên", "Tên Sinh Viên", "Lớp", "Giới Tính", "Xét Đồ Án"
            }
        ));
        table_QuanLySinhVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_QuanLySinhVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_QuanLySinhVien);

        jPanel12.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 820, 600));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 153, 153));
        jLabel17.setText("Mã sinh viên: ");
        jPanel12.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 130, 30));

        tabQLSV_tf_maSV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabQLSV_tf_maSV.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tabQLSV_tf_maSVCaretUpdate(evt);
            }
        });
        jPanel12.add(tabQLSV_tf_maSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 200, 30));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 153));
        jLabel18.setText("Tên sinh viên:");
        jPanel12.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 130, 30));

        tabQLSV_tf_tenSV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tabQLSV_tf_tenSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 200, 30));

        tabQLSV_tf_Lop.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tabQLSV_tf_Lop, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 200, 30));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 153, 153));
        jLabel19.setText("Lớp:");
        jPanel12.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 130, 30));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 153, 153));
        jLabel21.setText("Giới tính:");
        jPanel12.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 130, 30));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 153, 153));
        jLabel24.setText("Xét đồ án:");
        jPanel12.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 130, 30));

        tabQLSV_cb_XetDA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLSV_cb_XetDA.setForeground(new java.awt.Color(255, 51, 0));
        tabQLSV_cb_XetDA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ĐƯỢC XÉT", "KHÔNG ĐỦ ĐIỀU KIỆN" }));
        jPanel12.add(tabQLSV_cb_XetDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, 200, 30));

        tabQLSV_btn_Thoat.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_Thoat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_Thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabQLSV_btn_Thoat.setText("Thoát");
        tabQLSV_btn_Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_ThoatActionPerformed(evt);
            }
        });
        jPanel12.add(tabQLSV_btn_Thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 580, 180, -1));

        tabQLSV_btn_ThemMoi.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_ThemMoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_ThemMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabQLSV_btn_ThemMoi.setText("Thêm mới");
        tabQLSV_btn_ThemMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_ThemMoiActionPerformed(evt);
            }
        });
        jPanel12.add(tabQLSV_btn_ThemMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 180, -1));

        tabQLSV_btn_Sua.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_Sua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_Sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabQLSV_btn_Sua.setText("Sửa");
        tabQLSV_btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_SuaActionPerformed(evt);
            }
        });
        jPanel12.add(tabQLSV_btn_Sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 480, 180, -1));

        tabQLSV_btn_Xoa.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_Xoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_Xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_recycle_50px.png"))); // NOI18N
        tabQLSV_btn_Xoa.setText("Xóa");
        tabQLSV_btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_XoaActionPerformed(evt);
            }
        });
        jPanel12.add(tabQLSV_btn_Xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 180, -1));

        tabQLSV_rdb_Nam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(tabQLSV_rdb_Nam);
        tabQLSV_rdb_Nam.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabQLSV_rdb_Nam.setForeground(new java.awt.Color(0, 153, 153));
        tabQLSV_rdb_Nam.setText("NAM");
        jPanel12.add(tabQLSV_rdb_Nam, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 70, -1));

        tabQLSV_rdb_Nu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(tabQLSV_rdb_Nu);
        tabQLSV_rdb_Nu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabQLSV_rdb_Nu.setForeground(new java.awt.Color(0, 153, 153));
        tabQLSV_rdb_Nu.setText("NỮ");
        jPanel12.add(tabQLSV_rdb_Nu, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, 70, -1));

        tabPanel_SinhVien.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("tab5", tabPanel_SinhVien);

        tabPanel_GiangVien.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_GiangVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 153));
        jLabel11.setText("QUẢN LÝ GIẢNG VIÊN");
        jPanel13.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("Mã Giảng Viên:");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, -1, 40));

        tabQLGV_tf_maGV.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        tabQLGV_tf_maGV.setForeground(new java.awt.Color(0, 153, 153));
        jPanel13.add(tabQLGV_tf_maGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 210, 40));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 153, 153));
        jLabel25.setText("Tên Giảng Viên:");
        jPanel13.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, 40));

        tabQLGV_tf_tenGV.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        tabQLGV_tf_tenGV.setForeground(new java.awt.Color(0, 153, 153));
        jPanel13.add(tabQLGV_tf_tenGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, 210, 40));

        table_QLGV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_QLGV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Giảng Viên", "Tên Giảng Viên"
            }
        ));
        table_QLGV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_QLGVMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(table_QLGV);

        jPanel13.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, -1, 560));

        tabQLGV_btn_sua.setBackground(new java.awt.Color(255, 255, 255));
        tabQLGV_btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLGV_btn_sua.setForeground(new java.awt.Color(0, 153, 153));
        tabQLGV_btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabQLGV_btn_sua.setText("Sửa");
        tabQLGV_btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLGV_btn_suaActionPerformed(evt);
            }
        });
        jPanel13.add(tabQLGV_btn_sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 170, 60));

        tabQLGV_btn_xoa.setBackground(new java.awt.Color(255, 255, 255));
        tabQLGV_btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLGV_btn_xoa.setForeground(new java.awt.Color(0, 153, 153));
        tabQLGV_btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_recycle_50px.png"))); // NOI18N
        tabQLGV_btn_xoa.setText("Xóa");
        tabQLGV_btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLGV_btn_xoaActionPerformed(evt);
            }
        });
        jPanel13.add(tabQLGV_btn_xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 170, 60));

        tabQLGV_btn_thoat.setBackground(new java.awt.Color(255, 255, 255));
        tabQLGV_btn_thoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLGV_btn_thoat.setForeground(new java.awt.Color(0, 153, 153));
        tabQLGV_btn_thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabQLGV_btn_thoat.setText("Thoát");
        tabQLGV_btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLGV_btn_thoatActionPerformed(evt);
            }
        });
        jPanel13.add(tabQLGV_btn_thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 170, 60));

        tabQLGV_btn_them.setBackground(new java.awt.Color(255, 255, 255));
        tabQLGV_btn_them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLGV_btn_them.setForeground(new java.awt.Color(0, 153, 153));
        tabQLGV_btn_them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabQLGV_btn_them.setText("Thêm mới");
        tabQLGV_btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLGV_btn_themActionPerformed(evt);
            }
        });
        jPanel13.add(tabQLGV_btn_them, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 170, 60));

        tabPanel_GiangVien.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("tab6", tabPanel_GiangVien);

        tabPanel_HoiDong.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_HoiDong.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 153, 153));
        jLabel35.setText("QUẢN LÝ HỘI ĐỒNG");
        jPanel17.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 153));
        jLabel9.setText("Mã Hội Đồng: ");
        jPanel17.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, -1, 40));

        tabQLHD_tf_maHD.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        tabQLHD_tf_maHD.setForeground(new java.awt.Color(0, 153, 153));
        jPanel17.add(tabQLHD_tf_maHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 210, 40));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 153, 153));
        jLabel36.setText("Tên Hội Đồng:");
        jPanel17.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, 40));

        tabQLHD_tf_tenHD.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        tabQLHD_tf_tenHD.setForeground(new java.awt.Color(0, 153, 153));
        jPanel17.add(tabQLHD_tf_tenHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, 210, 40));

        table_QLHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_QLHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hội Đồng", "Tên Hội Đồng"
            }
        ));
        table_QLHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_QLHDMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(table_QLHD);

        jPanel17.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, -1, 560));

        tabQLHD_btn_sua.setBackground(new java.awt.Color(255, 255, 255));
        tabQLHD_btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLHD_btn_sua.setForeground(new java.awt.Color(0, 153, 153));
        tabQLHD_btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabQLHD_btn_sua.setText("Sửa");
        tabQLHD_btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLHD_btn_suaActionPerformed(evt);
            }
        });
        jPanel17.add(tabQLHD_btn_sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 170, 60));

        tabQLHD_btn_xoa.setBackground(new java.awt.Color(255, 255, 255));
        tabQLHD_btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLHD_btn_xoa.setForeground(new java.awt.Color(0, 153, 153));
        tabQLHD_btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_recycle_50px.png"))); // NOI18N
        tabQLHD_btn_xoa.setText("Xóa");
        tabQLHD_btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLHD_btn_xoaActionPerformed(evt);
            }
        });
        jPanel17.add(tabQLHD_btn_xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 170, 60));

        tabQLHD_btn_thoat.setBackground(new java.awt.Color(255, 255, 255));
        tabQLHD_btn_thoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLHD_btn_thoat.setForeground(new java.awt.Color(0, 153, 153));
        tabQLHD_btn_thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabQLHD_btn_thoat.setText("Thoát");
        tabQLHD_btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLHD_btn_thoatActionPerformed(evt);
            }
        });
        jPanel17.add(tabQLHD_btn_thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 170, 60));

        tabQLHD_btn_them.setBackground(new java.awt.Color(255, 255, 255));
        tabQLHD_btn_them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLHD_btn_them.setForeground(new java.awt.Color(0, 153, 153));
        tabQLHD_btn_them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabQLHD_btn_them.setText("Thêm mới");
        tabQLHD_btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLHD_btn_themActionPerformed(evt);
            }
        });
        jPanel17.add(tabQLHD_btn_them, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 170, 60));

        tabPanel_HoiDong.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("tab6", tabPanel_HoiDong);

        tabPanel_TieuBan.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_TieuBan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 153, 153));
        jLabel37.setText("QUẢN LÝ TIỂU BAN");
        jPanel18.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 153, 153));
        jLabel38.setText("Mã Tiểu Ban: ");
        jPanel18.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, -1, 40));

        tabQLTB_tf_maTB.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        tabQLTB_tf_maTB.setForeground(new java.awt.Color(0, 153, 153));
        jPanel18.add(tabQLTB_tf_maTB, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, 210, 40));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 153, 153));
        jLabel39.setText("Mã Hội Đồng Báo Cáo:");
        jPanel18.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, -1, 40));

        tabQLTB_tf_tenTB.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        tabQLTB_tf_tenTB.setForeground(new java.awt.Color(0, 153, 153));
        jPanel18.add(tabQLTB_tf_tenTB, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 260, 210, 40));

        table_QLTB.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_QLTB.setForeground(new java.awt.Color(0, 153, 153));
        table_QLTB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Tiểu Ban", "Tên Tiểu Ban", "Mã Hội Đồng Báo Cáo"
            }
        ));
        table_QLTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_QLTBMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(table_QLTB);

        jPanel18.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, -1, 560));

        tabQLTB_btn_sua.setBackground(new java.awt.Color(255, 255, 255));
        tabQLTB_btn_sua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLTB_btn_sua.setForeground(new java.awt.Color(0, 153, 153));
        tabQLTB_btn_sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabQLTB_btn_sua.setText("Sửa");
        tabQLTB_btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLTB_btn_suaActionPerformed(evt);
            }
        });
        jPanel18.add(tabQLTB_btn_sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 170, 60));

        tabQLTB_btn_xoa.setBackground(new java.awt.Color(255, 255, 255));
        tabQLTB_btn_xoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLTB_btn_xoa.setForeground(new java.awt.Color(0, 153, 153));
        tabQLTB_btn_xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_recycle_50px.png"))); // NOI18N
        tabQLTB_btn_xoa.setText("Xóa");
        tabQLTB_btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLTB_btn_xoaActionPerformed(evt);
            }
        });
        jPanel18.add(tabQLTB_btn_xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 590, 170, 60));

        tabQLTB_btn_thoat.setBackground(new java.awt.Color(255, 255, 255));
        tabQLTB_btn_thoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLTB_btn_thoat.setForeground(new java.awt.Color(0, 153, 153));
        tabQLTB_btn_thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabQLTB_btn_thoat.setText("Thoát");
        tabQLTB_btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLTB_btn_thoatActionPerformed(evt);
            }
        });
        jPanel18.add(tabQLTB_btn_thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 170, 60));

        tabQLTB_btn_them.setBackground(new java.awt.Color(255, 255, 255));
        tabQLTB_btn_them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabQLTB_btn_them.setForeground(new java.awt.Color(0, 153, 153));
        tabQLTB_btn_them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabQLTB_btn_them.setText("Thêm mới");
        tabQLTB_btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLTB_btn_themActionPerformed(evt);
            }
        });
        jPanel18.add(tabQLTB_btn_them, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 170, 60));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 153));
        jLabel40.setText("Tên Tiểu Ban:");
        jPanel18.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, -1, 40));

        tabQLTB_cb_maHDBC.setForeground(new java.awt.Color(0, 153, 153));
        jPanel18.add(tabQLTB_cb_maHDBC, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, 210, 40));

        tabPanel_TieuBan.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("tab6", tabPanel_TieuBan);

        tabPanel_PhanCongSinhVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablephancong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MÃ SINH VIÊN", "MÃ GIẢNG VIÊN DƯỚNG DẪN", "MÃ GIẢNG VIÊN PHẢN BIỆN", "MÃ TIỂU BAN"
            }
        ));
        jScrollPane8.setViewportView(jTablephancong);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 153, 153));
        jLabel26.setText("MÃ SINH VIÊN");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 153, 153));
        jLabel27.setText("MÃ GIẢNG VIÊN HƯỚNG DẪN");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 153, 153));
        jLabel30.setText("MÃ GIẢNG VIÊN PHẢN BIỆN");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 153, 153));
        jLabel31.setText("MÃ TIỂU BAN");

        jcbbgvhd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbbgvhdActionPerformed(evt);
            }
        });

        tabQLSV_btn_ThemMoi2.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_ThemMoi2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_ThemMoi2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabQLSV_btn_ThemMoi2.setText("Thêm mới");
        tabQLSV_btn_ThemMoi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_ThemMoi2ActionPerformed(evt);
            }
        });

        tabQLSV_btn_Sua2.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_Sua2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_Sua2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabQLSV_btn_Sua2.setText("Sửa");
        tabQLSV_btn_Sua2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_Sua2ActionPerformed(evt);
            }
        });

        tabQLSV_btn_Thoat2.setBackground(new java.awt.Color(255, 255, 255));
        tabQLSV_btn_Thoat2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV_btn_Thoat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabQLSV_btn_Thoat2.setText("Thoát");
        tabQLSV_btn_Thoat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSV_btn_Thoat2ActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 153, 153));
        jLabel41.setText("PHÂN CÔNG SINH VIÊN");

        jcbbmasv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbbmasvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dddd1Layout = new javax.swing.GroupLayout(dddd1);
        dddd1.setLayout(dddd1Layout);
        dddd1Layout.setHorizontalGroup(
            dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dddd1Layout.createSequentialGroup()
                .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dddd1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dddd1Layout.createSequentialGroup()
                            .addGap(48, 48, 48)
                            .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jcbbgvhd, 0, 140, Short.MAX_VALUE)
                                .addComponent(jcbbtb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbbgvpb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcbbmasv, 0, 140, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dddd1Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dddd1Layout.createSequentialGroup()
                                    .addComponent(tabQLSV_btn_ThemMoi2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addComponent(tabQLSV_btn_Sua2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(dddd1Layout.createSequentialGroup()
                                    .addGap(220, 220, 220)
                                    .addComponent(tabQLSV_btn_Thoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        dddd1Layout.setVerticalGroup(
            dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dddd1Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dddd1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jcbbmasv, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jcbbgvhd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jcbbgvpb, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jcbbtb, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(dddd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabQLSV_btn_ThemMoi2)
                    .addComponent(tabQLSV_btn_Sua2))
                .addGap(41, 41, 41)
                .addComponent(tabQLSV_btn_Thoat2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPanel_PhanCongSinhVien.add(dddd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, -1));

        tab_Center.addTab("Tab4", tabPanel_PhanCongSinhVien);

        tabPanel_PhanGVTB.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_PhanGVTB.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setText("PHÂN CÔNG TIỂU BAN");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 280, 50));

        table_PhanCongTieuBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Tiểu Ban", "Mã Giảng Viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(table_PhanCongTieuBan);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 100, 480, 580));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Mã Tiểu Ban");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, 30));

        tabPCTB_cb_MaTB.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_cb_MaTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCTB_cb_MaTBActionPerformed(evt);
            }
        });
        jPanel1.add(tabPCTB_cb_MaTB, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, 230, 30));

        tabPCTB_lb_GV5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_lb_GV5.setForeground(new java.awt.Color(0, 153, 153));
        tabPCTB_lb_GV5.setText("Mã Giảng Viên Thứ 5:");
        jPanel1.add(tabPCTB_lb_GV5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, -1, 30));

        tabPCTB_cb_SL.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_cb_SL.setForeground(new java.awt.Color(0, 153, 153));
        tabPCTB_cb_SL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "5" }));
        tabPCTB_cb_SL.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tabPCTB_cb_SLItemStateChanged(evt);
            }
        });
        tabPCTB_cb_SL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCTB_cb_SLActionPerformed(evt);
            }
        });
        jPanel1.add(tabPCTB_cb_SL, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, 230, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("Số Giảng Viên");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, 30));

        tabPCTB_lb_GV1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_lb_GV1.setForeground(new java.awt.Color(0, 153, 153));
        tabPCTB_lb_GV1.setText("Mã Giảng Viên Thứ 1:");
        jPanel1.add(tabPCTB_lb_GV1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, 30));

        tabPCTB_lb_GV2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_lb_GV2.setForeground(new java.awt.Color(0, 153, 153));
        tabPCTB_lb_GV2.setText("Mã Giảng Viên Thứ 2:");
        tabPCTB_lb_GV2.setToolTipText("");
        jPanel1.add(tabPCTB_lb_GV2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, 30));

        tabPCTB_lb_GV3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_lb_GV3.setForeground(new java.awt.Color(0, 153, 153));
        tabPCTB_lb_GV3.setText("Mã Giảng Viên Thứ 3:");
        jPanel1.add(tabPCTB_lb_GV3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, 30));

        tabPCTB_lb_GV4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_lb_GV4.setForeground(new java.awt.Color(0, 153, 153));
        tabPCTB_lb_GV4.setText("Mã Giảng Viên Thứ 4:");
        jPanel1.add(tabPCTB_lb_GV4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, 30));

        tabPCTB_tf_GV5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_tf_GV5.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tabPCTB_tf_GV5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 470, 230, 30));

        tabPCTB_tf_GV1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_tf_GV1.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tabPCTB_tf_GV1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 230, 30));

        tabPCTB_tf_GV2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_tf_GV2.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tabPCTB_tf_GV2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 230, 30));

        tabPCTB_tf_GV3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_tf_GV3.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tabPCTB_tf_GV3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 350, 230, 30));

        tabPCTB_tf_GV4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tabPCTB_tf_GV4.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tabPCTB_tf_GV4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 230, 30));

        tabPCTB_btn_ThemMoi.setBackground(new java.awt.Color(255, 255, 255));
        tabPCTB_btn_ThemMoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabPCTB_btn_ThemMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabPCTB_btn_ThemMoi.setText("Thêm mới");
        tabPCTB_btn_ThemMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCTB_btn_ThemMoiActionPerformed(evt);
            }
        });
        jPanel1.add(tabPCTB_btn_ThemMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 530, 180, -1));

        tabPCTB_btn_Sua.setBackground(new java.awt.Color(255, 255, 255));
        tabPCTB_btn_Sua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabPCTB_btn_Sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabPCTB_btn_Sua.setText("Sửa");
        tabPCTB_btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCTB_btn_SuaActionPerformed(evt);
            }
        });
        jPanel1.add(tabPCTB_btn_Sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 530, 180, -1));

        tabPCTB_btn_Xoa.setBackground(new java.awt.Color(255, 255, 255));
        tabPCTB_btn_Xoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabPCTB_btn_Xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_recycle_50px.png"))); // NOI18N
        tabPCTB_btn_Xoa.setText("Xóa");
        tabPCTB_btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCTB_btn_XoaActionPerformed(evt);
            }
        });
        jPanel1.add(tabPCTB_btn_Xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 620, 180, -1));

        tabPCTB_btn_Thoat.setBackground(new java.awt.Color(255, 255, 255));
        tabPCTB_btn_Thoat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabPCTB_btn_Thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_50px.png"))); // NOI18N
        tabPCTB_btn_Thoat.setText("Thoát");
        tabPCTB_btn_Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCTB_btn_ThoatActionPerformed(evt);
            }
        });
        jPanel1.add(tabPCTB_btn_Thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 620, 180, -1));

        table_PhanCongTieuBan_GV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Giảng Viên"
            }
        ));
        table_PhanCongTieuBan_GV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane6.setViewportView(table_PhanCongTieuBan_GV);

        jPanel1.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 220, 580));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("Giảng Viên Còn Lại");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, -1, -1));

        tabPanel_PhanGVTB.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("tab7", tabPanel_PhanGVTB);

        tabPanel_TimKiem.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel_TimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 153, 153));
        jLabel14.setText("DANH SÁCH SINH VIÊN");
        jPanel14.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

        table_TimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_TimKiem.setForeground(new java.awt.Color(0, 153, 153));
        table_TimKiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sinh Viên", "Tên Sinh Viên", "Lớp", "Giới Tính", "Xét Đồ Án"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_TimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_TimKiemMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_TimKiem);

        jPanel14.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 1280, 560));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 153, 153));
        jLabel20.setText("Tìm kiếm:");
        jPanel14.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 130, 40));

        tabTimKiem_tf_maSV.setBackground(new java.awt.Color(0, 153, 153));
        tabTimKiem_tf_maSV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabTimKiem_tf_maSV.setForeground(new java.awt.Color(255, 255, 255));
        tabTimKiem_tf_maSV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tabTimKiem_tf_maSV.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tabTimKiem_tf_maSVCaretUpdate(evt);
            }
        });
        tabTimKiem_tf_maSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabTimKiem_tf_maSVActionPerformed(evt);
            }
        });
        tabTimKiem_tf_maSV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tabTimKiem_tf_maSVKeyTyped(evt);
            }
        });
        jPanel14.add(tabTimKiem_tf_maSV, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 80, 340, 40));

        tabPanel_TimKiem.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1340, 720));

        tab_Center.addTab("tab5", tabPanel_TimKiem);

        jPanel9.setBackground(new java.awt.Color(0, 153, 153));

        jComboBox_Lop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_LopActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Chọn lớp");

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 204, 204));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("THỐNG KÊ DANH SÁCH SINH VIÊN THEO LỚP");

        jTable_DS_SV_DOAN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MÃ SỐ SINH VIÊN", "HỌ TÊN", "GIỚI TÍNH", "MÃ ĐỒ ÁN TỐT NGHIỆP", "KẾT QUẢ", "XẾP LOẠI"
            }
        ));
        jScrollPane9.setViewportView(jTable_DS_SV_DOAN);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(409, 409, 409)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jComboBox_Lop, 0, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_Lop, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(609, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout tabPanel_ThongKeSVLayout = new javax.swing.GroupLayout(tabPanel_ThongKeSV);
        tabPanel_ThongKeSV.setLayout(tabPanel_ThongKeSVLayout);
        tabPanel_ThongKeSVLayout.setHorizontalGroup(
            tabPanel_ThongKeSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabPanel_ThongKeSVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabPanel_ThongKeSVLayout.setVerticalGroup(
            tabPanel_ThongKeSVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPanel_ThongKeSVLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab_Center.addTab("tab8", tabPanel_ThongKeSV);

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("THỐNG KÊ DANH SÁCH GIÁO VIÊN");

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("TIỂU BAN");

        jComboBox_TieuBan.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jComboBox_TieuBan.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox_TieuBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_TieuBanActionPerformed(evt);
            }
        });

        jTable_GV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ GIÁO VIÊN", "TÊN GIÁO VIÊN"
            }
        ));
        jScrollPane10.setViewportView(jTable_GV);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(jLabel32)
                .addContainerGap(643, Short.MAX_VALUE))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jComboBox_TieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel32)
                .addGap(26, 26, 26)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_TieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(607, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout tabPanel_ThongKeGVLayout = new javax.swing.GroupLayout(tabPanel_ThongKeGV);
        tabPanel_ThongKeGV.setLayout(tabPanel_ThongKeGVLayout);
        tabPanel_ThongKeGVLayout.setHorizontalGroup(
            tabPanel_ThongKeGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPanel_ThongKeGVLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabPanel_ThongKeGVLayout.setVerticalGroup(
            tabPanel_ThongKeGVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tab_Center.addTab("tab9", tabPanel_ThongKeGV);

        jLabel34.setText("THEO LỚP");

        jComboBox_ThongkeDiemTheoLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ThongkeDiemTheoLopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_bieudoLayout = new javax.swing.GroupLayout(jPanel_bieudo);
        jPanel_bieudo.setLayout(jPanel_bieudoLayout);
        jPanel_bieudoLayout.setHorizontalGroup(
            jPanel_bieudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 997, Short.MAX_VALUE)
        );
        jPanel_bieudoLayout.setVerticalGroup(
            jPanel_bieudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox_ThongkeDiemTheoLop, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jPanel_bieudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(jComboBox_ThongkeDiemTheoLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 618, Short.MAX_VALUE))
                    .addComponent(jPanel_bieudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout tabPanel_ThongKeDiemLayout = new javax.swing.GroupLayout(tabPanel_ThongKeDiem);
        tabPanel_ThongKeDiem.setLayout(tabPanel_ThongKeDiemLayout);
        tabPanel_ThongKeDiemLayout.setHorizontalGroup(
            tabPanel_ThongKeDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabPanel_ThongKeDiemLayout.setVerticalGroup(
            tabPanel_ThongKeDiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPanel_ThongKeDiemLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab_Center.addTab("tab10", tabPanel_ThongKeDiem);

        Panel_Tong.add(tab_Center, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -45, 1370, 790));

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(0, 153, 153));

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setForeground(new java.awt.Color(0, 153, 153));
        jMenu2.setText("  Hệ thống  ");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jMenu2.setOpaque(true);
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        menuItem_DangNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        menuItem_DangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_user_16px.png"))); // NOI18N
        menuItem_DangNhap.setText("Đăng nhập");
        menuItem_DangNhap.setEnabled(false);
        menuItem_DangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuItem_DangNhapMouseClicked(evt);
            }
        });
        menuItem_DangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_DangNhapActionPerformed(evt);
            }
        });
        jMenu2.add(menuItem_DangNhap);

        menuItem_Quanly.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        menuItem_Quanly.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_manager_16px.png"))); // NOI18N
        menuItem_Quanly.setText("Quản lý người dùng");
        menuItem_Quanly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_QuanlyActionPerformed(evt);
            }
        });
        jMenu2.add(menuItem_Quanly);

        menuItem_DangXuat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        menuItem_DangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_login_16px.png"))); // NOI18N
        menuItem_DangXuat.setText("Đăng xuất");
        menuItem_DangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_DangXuatActionPerformed(evt);
            }
        });
        jMenu2.add(menuItem_DangXuat);
        jMenu2.add(jSeparator2);
        jMenu2.add(jSeparator1);

        menuItem_Exit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        menuItem_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_exit_16px.png"))); // NOI18N
        menuItem_Exit.setText("Exit");
        menuItem_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuItem_ExitMouseClicked(evt);
            }
        });
        menuItem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_ExitActionPerformed(evt);
            }
        });
        jMenu2.add(menuItem_Exit);
        jMenu2.add(jSeparator3);

        jMenuBar1.add(jMenu2);

        jMenu3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.setForeground(new java.awt.Color(0, 153, 153));
        jMenu3.setText("  Quản lý  ");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jMenu3.setOpaque(true);

        tabQLSV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_student_male_16px.png"))); // NOI18N
        tabQLSV.setText("Sinh Viên");
        tabQLSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLSVActionPerformed(evt);
            }
        });
        jMenu3.add(tabQLSV);

        tabQLGV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLGV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_teacher_16px.png"))); // NOI18N
        tabQLGV.setText("Giảng viên");
        tabQLGV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLGVActionPerformed(evt);
            }
        });
        jMenu3.add(tabQLGV);

        tabQLHD.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_scania_16px.png"))); // NOI18N
        tabQLHD.setText("Hội Đồng");
        tabQLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLHDActionPerformed(evt);
            }
        });
        jMenu3.add(tabQLHD);

        tabQLTB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabQLTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_group_16px.png"))); // NOI18N
        tabQLTB.setText("Tiểu Ban");
        tabQLTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabQLTBActionPerformed(evt);
            }
        });
        jMenu3.add(tabQLTB);

        tabPCSV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabPCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_task_16px_1.png"))); // NOI18N
        tabPCSV.setText("Phân Công Sinh Viên");
        tabPCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPCSVActionPerformed(evt);
            }
        });
        jMenu3.add(tabPCSV);

        tabPGVTB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabPGVTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_group_16px.png"))); // NOI18N
        tabPGVTB.setText("Phân Giáo Viên Tiểu Ban");
        tabPGVTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabPGVTBActionPerformed(evt);
            }
        });
        jMenu3.add(tabPGVTB);

        jMenuBar1.add(jMenu3);

        jMenu5.setBackground(new java.awt.Color(255, 255, 255));
        jMenu5.setForeground(new java.awt.Color(0, 153, 153));
        jMenu5.setText("  Tìm kiếm  ");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jMenu5.setOpaque(true);

        tabTiemKiem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        tabTiemKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tabTiemKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_search_16px.png"))); // NOI18N
        tabTiemKiem.setText("Thông tin của Sinh viên");
        tabTiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabTiemKiemActionPerformed(evt);
            }
        });
        jMenu5.add(tabTiemKiem);

        jMenuBar1.add(jMenu5);

        jMenu4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.setForeground(new java.awt.Color(0, 153, 153));
        jMenu4.setText("  Thống kê  ");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jMenu4.setOpaque(true);

        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_list_16px.png"))); // NOI18N
        jMenuItem13.setText("Danh sách sinh viên");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_tasklist_16px.png"))); // NOI18N
        jMenuItem14.setText("Danh sách giảng viên");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_table_16px.png"))); // NOI18N
        jMenuItem15.setText("Điểm tổng kết của sinh viên");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Tong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Tong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItem_DangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItem_DangNhapMouseClicked

    }//GEN-LAST:event_menuItem_DangNhapMouseClicked

    private void menuItem_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItem_ExitMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItem_ExitMouseClicked

    private void menuItem_DangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_DangXuatActionPerformed
        dispose();
        Login lg = new Login();
        lg.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_menuItem_DangXuatActionPerformed

    private void menuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItem_ExitActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void menuItem_DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_DangNhapActionPerformed

    }//GEN-LAST:event_menuItem_DangNhapActionPerformed

    private void menuItem_QuanlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_QuanlyActionPerformed
        tab_Center.setSelectedIndex(1);// TODO add your handling code here:
        tabQLND_lb_maDN.setVisible(false);
        tabQLND_lb_MK.setVisible(false);
        hienThiBangQLND();
    }//GEN-LAST:event_menuItem_QuanlyActionPerformed

    private void tabQLND_tf_MatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLND_tf_MatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabQLND_tf_MatKhauActionPerformed

    private void tabQLND_btn_ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLND_btn_ThoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát", "Warning!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            tab_Center.setSelectedIndex(0);
        }

    }//GEN-LAST:event_tabQLND_btn_ThoatActionPerformed

    public void resetTabQLND() {
        tabQLND_tf_MaDN.setText("");
        tabQLND_tf_MatKhau.setText("");
        tabQLND_tf_MaDN.requestFocus();
        tabQLND_lb_maDN.setVisible(false);
        tabQLND_lb_MK.setVisible(false);
    }

    public void suaTaiKhoan(String maDN, String matKhau) {
        String mkmahoa = ma_Hoa_MK(matKhau);
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("update taikhoan set matkhau='" + mkmahoa + "' where madangnhap='" + maDN + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tài khoản", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    System.out.println("Cập nhật mật khẩu thành công");
                    resetTabQLND();
                    hienThiBangQLND();
                }
            }
        } catch (SQLException | HeadlessException ex) {
            System.out.println("Sửa tài khoản không thành công");
        }
    }
    private void tabQLND_btn_ChinhSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLND_btn_ChinhSuaActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String maDN = CH.KiemTraMaDN(tabQLND_tf_MaDN.getText().toString().trim());
        System.out.println(maDN);
        String matKhau = CH.KiemTraMatKhau(tabQLND_tf_MatKhau.getText().toString().trim());
        System.out.println(matKhau);
        if (maDN.equals("0")) {
            tabQLND_lb_maDN.setVisible(true);
            tabQLND_tf_MaDN.requestFocus();
        } else if (matKhau.equals("0")) {
            tabQLND_lb_maDN.setVisible(false);
            tabQLND_lb_MK.setVisible(true);
            tabQLND_tf_MatKhau.requestFocus();
        } else if (!maDN.equals("0") && !matKhau.equals("0")) {
            tabQLND_lb_MK.setVisible(false);
            suaTaiKhoan(maDN, matKhau);
        }
    }//GEN-LAST:event_tabQLND_btn_ChinhSuaActionPerformed

    private void table_QuanLyNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_QuanLyNguoiDungMouseClicked
        int i = table_QuanLyNguoiDung.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_QuanLyNguoiDung.getModel();
        tabQLND_tf_MaDN.setText(model.getValueAt(i, 0).toString().trim());
        tabQLND_tf_MatKhau.setText(model.getValueAt(i, 1).toString().trim());

    }//GEN-LAST:event_table_QuanLyNguoiDungMouseClicked

    private void tabQLSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSVActionPerformed
        tab_Center.setSelectedIndex(2);
        tabQLSV_rdb_Nam.isSelected();
        //table_QuanLySinhVien.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_tabQLSVActionPerformed
    public void resetTabQLSV() {
        tabQLSV_tf_maSV.setText("");
        tabQLSV_tf_tenSV.setText("");
        tabQLSV_tf_Lop.setText("");
        tabQLSV_rdb_Nu.isSelected();
        tabQLSV_cb_XetDA.setSelectedIndex(0);
        tabQLSV_tf_maSV.requestFocus();
    }

    public boolean checkTabQLSV() {
        return !(tabQLSV_tf_maSV.getText().isEmpty() || tabQLSV_tf_tenSV.getText().isEmpty()
                || tabQLSV_tf_Lop.getText().isEmpty());
    }

    public void themTaiKhoanSV(String maSV, int xetDA) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pssv = con.prepareStatement("insert into TAIKHOANSV values('" + maSV + "', '" + maSV + "')");
            PreparedStatement pstk = con.prepareStatement("insert into TAIKHOAN values('" + maSV + "', '" + ma_Hoa_MK("123456") + "', '" + 2 + "')");

            if (pstk.executeUpdate() > 0) {
                System.out.println("Thêm tài khoản thành công");
            }
            if (pssv.executeUpdate() > 0) {
                System.out.println("Thêm tài khoản SV vào Tài Khoản thành công");
            }
            if (xetDA == 1) {
                PreparedStatement psctda = con.prepareStatement("insert into CT_DOAN (masv) values('" + maSV + "')");
                if (psctda.executeUpdate() > 0) {
                    System.out.println("Thêm sinh viên vào chi tiết đồ án thành công");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm Sinh viên vào Tài Khoản Sinh viên lỗi");
        }
    }

    public String layTaiMaDA_CTDA(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_doan where masv='" + maSV + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("madoan");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public Boolean kt_xetdoan(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from sinhvien where masv='" + maSV + "' and xetdoan=0");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void xoasv_ctdoan(String maSV) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement psctda = con.prepareStatement("delete from CT_DOAN where masv='" + maSV + "'");
            psctda.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean kt_ttgvhd_ctdoan(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_doan where masv='" + maSV + "' and magvhd is null");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void xoaTaiKhoanSV(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
//            PreparedStatement psctda = con.prepareStatement("delete from CT_DOAN where masv='" + maSV + "'");
            PreparedStatement pssv = con.prepareStatement("delete from TAIKHOANSV where masv='" + maSV + "' ");
            PreparedStatement pstk = con.prepareStatement("delete from TAIKHOAN where madangnhap='" + maSV + "'");
            PreparedStatement ps = con.prepareStatement("delete from sinhvien where masv='" + maSV + "'");
//            PreparedStatement psmada = con.prepareStatement("delete from cham where madoan='" + layTaiMaDA_CTDA(maSV) + "'");
            int dialogResult = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa sinh viên", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
//                if (psmada.executeUpdate() > 0) {
//                    System.out.println("Xóa madoan trong bảng chấm");
//                }
//                if (psctda.executeUpdate() > 0) {
//                    System.out.println("Xóa trong chi tiết đồ án thành công");
//                }
                if (pssv.executeUpdate() > 0) {
                    System.out.println("Xóa tài khoản sinh viên thành công");
                }
                if (pstk.executeUpdate() > 0) {
                    System.out.println("Xóa tài khoản thành công");
                }
                if (ps.executeUpdate() > 0) {
                    System.out.println("Xóa Sinh viên thành công");
                    resetTabQLSV();
                    hienThiBangQLSV();
                }
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Xóa Sinh viên lỗi");
        }
    }
    private void tabQLSV_btn_ThemMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_ThemMoiActionPerformed
        if (checkTabQLSV()) {
            ChuanHoa CH = new ChuanHoa();
            String maSV = CH.KiemTraMSV(tabQLSV_tf_maSV.getText());
            String tenSV = CH.ChuanHoaTen(tabQLSV_tf_tenSV.getText());
            String lop = CH.KiemTraLop(tabQLSV_tf_Lop.getText());
            if (!maSV.equals("0") && !(CH.KiemTraTen(ChuanHoa.removeAccent(tabQLSV_tf_tenSV.getText())).equals("0")) && !lop.equals("0")) {
                String gioiTinh = null;
                if (tabQLSV_rdb_Nam.isSelected()) {
                    gioiTinh = "NAM";
                }
                if (tabQLSV_rdb_Nu.isSelected()) {
                    gioiTinh = "NỮ";
                }
                String xetDA = String.valueOf(tabQLSV_cb_XetDA.getSelectedItem());
                int xet;
                if (xetDA.equals("ĐƯỢC XÉT")) {
                    xet = 1;
                } else {
                    xet = 0;
                }
                try {
                    String sql_Them = "insert into sinhvien values ('" + maSV + "',N'" + tenSV + "','" + lop + "',N'" + gioiTinh + "','" + xet + "')";
                    Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql_Them);
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm sinh viên", "Warning!", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        if (ps.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "Ban da them Sinh vien thanh cong");
                            resetTabQLSV();
                            hienThiBangQLSV();
                            themTaiKhoanSV(maSV, xet);
                        } else {
                            JOptionPane.showMessageDialog(this, "Them Sinh vien không thành công");
                        }
                    }
                    con.close();
                } catch (SQLException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "Sinh viên đã tồn tại không thể thêm");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thông tin được nhập không hợp lệ");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        }
    }//GEN-LAST:event_tabQLSV_btn_ThemMoiActionPerformed
    public boolean tonTaiMaSV(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from sinhvien where masv='" + maSV + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {

        }
        return false;
    }

    private String xetDoAnSV(String maSV) {
        String a = "";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from sinhvien where masv='" + maSV + "' and xetdoan='1'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = "1";
            } else {
                a = "0";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tìm ra sinh viên");
        }

        return a;
    }
    private void tabQLSV_btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_SuaActionPerformed
        if (checkTabQLSV()) {
            ChuanHoa CH = new ChuanHoa();
            String maSV = CH.KiemTraMSV(tabQLSV_tf_maSV.getText());
            String tenSV = CH.ChuanHoaTen(tabQLSV_tf_tenSV.getText());
            String lop = CH.KiemTraLop(tabQLSV_tf_Lop.getText());
            String xetdoan = xetDoAnSV(maSV);
            if (!maSV.equals("0") && !(CH.KiemTraTen(ChuanHoa.removeAccent(tabQLSV_tf_tenSV.getText())).equals("0")) && !lop.equals("0")) {
                String gioiTinh = null;
                if (tabQLSV_rdb_Nam.isSelected()) {
                    gioiTinh = "NAM";
                }
                if (tabQLSV_rdb_Nu.isSelected()) {
                    gioiTinh = "NỮ";
                }
                String xetDA = String.valueOf(tabQLSV_cb_XetDA.getSelectedItem());
                int xet;
                if (xetDA.equals("ĐƯỢC XÉT")) {
                    xet = 1;
                } else {
                    xet = 0;
                }
                if (tonTaiMaSV(maSV)) {
                    try {
                        String url = "jdbc:sqlserver://;databaseName=QLDASV;user=sa;password=123";
                        try ( Connection con = DriverManager.getConnection(url)) {
                            PreparedStatement ps = con.prepareStatement("update sinhvien set tensv=N'" + tenSV + "', lop= '" + lop + "', gioitinh=N'" + gioiTinh + "', xetdoan='" + xet + "' where masv='" + maSV + "'");
                            PreparedStatement psxoa = con.prepareStatement("delete from ct_doan where masv='" + maSV + "'");
                            PreparedStatement psthem = con.prepareStatement("insert into ct_doan (masv) values ('" + maSV + "') ");
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa sinh viên?", "Warning!", JOptionPane.YES_NO_OPTION);
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                if ((xet == 1) && xetdoan == "0") {
                                    if (psthem.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(this, "Thêm sv vào ct_doan thành công");
                                    }
                                }
                                if ((xet == 0) && xetdoan == "1") {
                                    if (psxoa.executeUpdate() > 0) {
                                        System.out.println("Xoa sv khỏi ct_doan");
                                    }
                                }
                                if (ps.executeUpdate() > 0) {
                                    JOptionPane.showMessageDialog(null, "Bạn đã sửa sinh viên thành công");
                                    resetTabQLSV();
                                    hienThiBangQLSV();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Sửa sinh viên không thành công");
                                }
                            }
                        }
                    } catch (SQLException | HeadlessException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi truy vấn");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Sinh viên chưa có không thể sửa");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Không đúng định dạng");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        }


    }//GEN-LAST:event_tabQLSV_btn_SuaActionPerformed

    private void table_QuanLySinhVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_QuanLySinhVienMouseClicked
        int i = table_QuanLySinhVien.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_QuanLySinhVien.getModel();
        tabQLSV_tf_maSV.setText(model.getValueAt(i, 0).toString());
        tabQLSV_tf_tenSV.setText(model.getValueAt(i, 1).toString());
        tabQLSV_tf_Lop.setText(model.getValueAt(i, 2).toString());
        String gioiTinh = model.getValueAt(i, 3).toString();
        if (gioiTinh.equals("NAM")) {
            tabQLSV_rdb_Nam.setSelected(true);
        } else {
            tabQLSV_rdb_Nu.setSelected(true);
        }
        String xetDA = model.getValueAt(i, 4).toString();
        if (xetDA.equals("ĐƯỢC XÉT")) {
            tabQLSV_cb_XetDA.setSelectedIndex(0);
        } else {
            tabQLSV_cb_XetDA.setSelectedIndex(1);
        }
    }//GEN-LAST:event_table_QuanLySinhVienMouseClicked

    private void tabQLSV_btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_XoaActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String maSV = CH.KiemTraMSV(tabQLSV_tf_maSV.getText().toString());
        if (!maSV.equals("0")) {
            if (kt_xetdoan(maSV)) {
                xoaTaiKhoanSV(maSV);
            } else {
                if (kt_ttgvhd_ctdoan(maSV)) {
                    xoaTaiKhoanSV(maSV);
                    xoasv_ctdoan(maSV);
                } else {
                    JOptionPane.showMessageDialog(this, "Sinh viên đã được phân giảng viên hướng dẫn, không được xóa");
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "Thông tin cần xóa không đúng");
        }
    }//GEN-LAST:event_tabQLSV_btn_XoaActionPerformed

    private void tabQLSV_tf_maSVCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tabQLSV_tf_maSVCaretUpdate
        String maSV = tabQLSV_tf_maSV.getText();
        try {
            String sql_Caret = "Select * from sinhvien where masv=?";
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql_Caret);
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tabQLSV_tf_tenSV.setText(rs.getString("tensv"));
                tabQLSV_tf_Lop.setText(rs.getString("lop"));
                String gioiTinh = rs.getString("gioitinh");
                if (gioiTinh.equals("NAM")) {
                    tabQLSV_rdb_Nam.setSelected(true);
                } else {
                    tabQLSV_rdb_Nu.setSelected(true);
                }
                int xetDA = rs.getInt("xetdoan");
                if (xetDA == 1) {
                    tabQLSV_cb_XetDA.setSelectedIndex(0);
                } else {
                    tabQLSV_cb_XetDA.setSelectedIndex(1);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên cần xóa");
        }
    }//GEN-LAST:event_tabQLSV_tf_maSVCaretUpdate

    private void tabQLSV_btn_ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_ThoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm sinh viên", "Warning!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            tab_Center.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabQLSV_btn_ThoatActionPerformed

    private void tabTiemKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabTiemKiemActionPerformed
        tab_Center.setSelectedIndex(8);
        table_TimKiem.setAutoCreateRowSorter(true);
        hienThiBangQLSV();


    }//GEN-LAST:event_tabTiemKiemActionPerformed

    private void table_TimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_TimKiemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_TimKiemMouseClicked

    //Tạo danh sách đối tượng Sinh Viên
    public ArrayList<SinhVien> ListSinhVien(String maSinhVien) {
        ArrayList<SinhVien> sinhVienList = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try {
            Connection con = DBConnection.getConnection();
            st = con.createStatement();
            String sql_TimKiem = "SELECT * FROM SINHVIEN WHERE CONCAT(MASV, TENSV, LOP, GIOITINH, XETDOAN) LIKE '%" + maSinhVien + "%'";
            rs = st.executeQuery(sql_TimKiem);

            SinhVien sinhVien;
            while (rs.next()) {
                sinhVien = new SinhVien(rs.getString("masv"),
                        rs.getString("tensv"),
                        rs.getString("lop"),
                        rs.getString("gioitinh"),
                        rs.getInt("xetdoan"));
                sinhVienList.add(sinhVien);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "LOI ARRAYLIST SINH VIEN");
        }
        return sinhVienList;
    }

    public void timKiemSV() {
        ArrayList<SinhVien> sinhVien = ListSinhVien(tabTimKiem_tf_maSV.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Mã Sinh Viên", "Tên Sinh Viên", "Lớp", "Giới Tính", "Xét Đồ Án"});
        Object[] row = new Object[5];
        for (int i = 0; i < sinhVien.size(); i++) {
            row[0] = sinhVien.get(i).getMSSV();
            row[1] = sinhVien.get(i).getTenSV();
            row[2] = sinhVien.get(i).getLop();
            row[3] = sinhVien.get(i).getGioiTinh();
            row[4] = sinhVien.get(i).getXetDA();
            model.addRow(row);
        }
        table_TimKiem.setModel(model);

    }
    private void tabTimKiem_tf_maSVCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tabTimKiem_tf_maSVCaretUpdate
        timKiemSV();
    }//GEN-LAST:event_tabTimKiem_tf_maSVCaretUpdate

    public boolean tonTaiMaDA(String maDA) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from doan where madoan='" + maDA + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

//    public void themDA(String maDA, String tenDA) {
//        try {
//            Connection con = DBConnection.getConnection();
//            PreparedStatement ps = con.prepareStatement("insert into DOAN values ('" + maDA + "',N'" + tenDA + "')");
//            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới đồ án", "Warning!", JOptionPane.YES_NO_OPTION);
//            if (dialogResult == JOptionPane.YES_OPTION) {
//                if (ps.executeUpdate() > 0) {
//                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công đồ án " + maDA);
//                    hienThiBangDoAn();
//                    lamMoiTabDA();
//                }
//            }
//        } catch (SQLException | HeadlessException e) {
//        }
//    }
//
//    public void suaDA(String maDA, String tenDA) {
//        try {
//            Connection con = DBConnection.getConnection();
//            PreparedStatement ps = con.prepareStatement("update DOAN set tendoan=N'" + tenDA + "' where madoan='" + maDA + "'");
//            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tên đồ án", "Warning!", JOptionPane.YES_NO_OPTION);
//            if (dialogResult == JOptionPane.YES_OPTION) {
//                if (ps.executeUpdate() > 0) {
//                    JOptionPane.showMessageDialog(this, "Bạn đã sửa thành công đồ án " + maDA);
//                    hienThiBangDoAn();
//                    lamMoiTabDA();
//                }
//            }
//        } catch (SQLException | HeadlessException e) {
//        }
//    }
//
//    private void xoaDA(String maDA) {
//        try {
//            Connection con = DBConnection.getConnection();
//            PreparedStatement ps = con.prepareStatement("delete from DOAN where madoan='" + maDA + "'");
//            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa đồ án", "Warning!", JOptionPane.YES_NO_OPTION);
//            if (dialogResult == JOptionPane.YES_OPTION) {
//                if (ps.executeUpdate() > 0) {
//                    JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công đồ án " + maDA);
//                    hienThiBangDoAn();
//                    lamMoiTabDA();
//                }
//            }
//        } catch (SQLException | HeadlessException e) {
//        }
//    }
    private void tabPCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCSVActionPerformed
        tab_Center.setSelectedIndex(6);
        jcbbgvhd.removeAllItems();
        jcbbgvpb.removeAllItems();
        jcbbtb.removeAllItems();
        loadMaSV();
        loadPhanCong();
        jLabel27.setVisible(false);
        jLabel30.setVisible(false);
        jLabel31.setVisible(false);
        jcbbgvhd.setVisible(false);
        jcbbgvpb.setVisible(false);
        jcbbtb.setVisible(false);
    }//GEN-LAST:event_tabPCSVActionPerformed

//tab PHÂN CÔNG TIỂU BAN ======================================================================================================================================//    
    public boolean kiemTraTieuBan(String s) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select * from ct_tieuban where matieuban=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            return rs.next(); //JOptionPane.showMessageDialog(this, "Mã tiểu ban chưa có giảng viên tồn tại");
        } catch (Exception e) {
        }
        return false;
    }

    public boolean kiemTraGVTieuBan(String s) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select * from ct_tieuban where magv=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            return rs.next(); //JOptionPane.showMessageDialog(this, "Mã tiểu ban chưa có giảng viên tồn tại");
        } catch (Exception e) {
        }
        return false;
    }

    public void resetNhapPCTB() {
        tabPCTB_cb_MaTB.requestFocus();
        tabPCTB_tf_GV1.setText("");
        tabPCTB_tf_GV2.setText("");
        tabPCTB_tf_GV3.setText("");
        tabPCTB_tf_GV4.setText("");
        tabPCTB_tf_GV5.setText("");

    }
    private void tabPCTB_btn_ThemMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCTB_btn_ThemMoiActionPerformed
        int sl = tabPCTB_cb_SL.getSelectedIndex();
        ChuanHoa CH = new ChuanHoa();
        String matb = String.valueOf(tabPCTB_cb_MaTB.getSelectedItem()).trim();
        String gv1 = tabPCTB_tf_GV1.getText();
        String gv2 = tabPCTB_tf_GV2.getText();
        String gv3 = tabPCTB_tf_GV3.getText();
        String gv4 = tabPCTB_tf_GV4.getText();
        String gv5 = tabPCTB_tf_GV5.getText();
        if (sl == 0) {
            if (tabPCTB_tf_GV1.getText().isEmpty() || tabPCTB_tf_GV2.getText().isEmpty() || tabPCTB_tf_GV3.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã Giảng viên không được để trống");
                tabPCTB_tf_GV1.requestFocus();
            } else if (kiemTraTieuBan(matb)) {
                JOptionPane.showMessageDialog(this, "Tiểu ban " + matb + " đã có thành viên, vui lòng chọn Tiểu ban khác!!!!");
                tabPCTB_cb_MaTB.requestFocus();
            } else if (!CH.KiemTraMGV(gv1).equals("0") && !CH.KiemTraMGV(gv2).equals("0") && !CH.KiemTraMGV(gv3).equals("0")) {
                gv1 = CH.KiemTraMGV(gv1);
                gv2 = CH.KiemTraMGV(gv2);
                gv3 = CH.KiemTraMGV(gv3);
                if (kiemTraGVTieuBan(gv1)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv1 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV1.requestFocus();
                } else if (kiemTraGVTieuBan(gv2)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv2 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV2.requestFocus();
                } else if (kiemTraGVTieuBan(gv3)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv3 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV3.requestFocus();
                } else {
                    try {
                        Connection con = DBConnection.getConnection();
                        String sql = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv1 + "')";
                        String sql2 = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv2 + "')";
                        String sql3 = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv3 + "')";
                        PreparedStatement ps = con.prepareStatement(sql);
                        PreparedStatement ps2 = con.prepareStatement(sql2);
                        PreparedStatement ps3 = con.prepareStatement(sql3);
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới tiểu ban", "Warning!", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            if (ps.executeUpdate() > 0) {
                                if (ps2.executeUpdate() > 0) {
                                    if (ps3.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công các giáo viên cho tiểu ban " + matb);
                                        hienThiBangTieuBan();
                                        hienThiBangGVConLai();
                                        resetNhapPCTB();
                                    }
                                }
                            }
                        }
                    } catch (SQLException | HeadlessException e) {
                        JOptionPane.showMessageDialog(this, "Giảng viên chưa được lưu!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã Giảng Viên Không Tồn Tại!!");
                tabPCTB_tf_GV1.requestFocus();
            }
        } else if (sl == 1) {
            if (tabPCTB_tf_GV1.getText().isEmpty() || tabPCTB_tf_GV2.getText().isEmpty() || tabPCTB_tf_GV3.getText().isEmpty() || tabPCTB_tf_GV4.getText().isEmpty() || tabPCTB_tf_GV5.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã Giảng viên không được để trống");
                tabPCTB_tf_GV1.requestFocus();
            } else if (kiemTraTieuBan(matb)) {
                JOptionPane.showMessageDialog(this, "Tiểu ban " + matb + " đã có thành viên, vui lòng chọn Tiểu ban khác!!!!");
                tabPCTB_cb_MaTB.requestFocus();
            } else if (!CH.KiemTraMGV(gv1).equals("0") && !CH.KiemTraMGV(gv2).equals("0") && !CH.KiemTraMGV(gv3).equals("0") && !CH.KiemTraMGV(gv4).equals("0") && !CH.KiemTraMGV(gv5).equals("0")) {
                gv1 = CH.KiemTraMGV(gv1);
                gv2 = CH.KiemTraMGV(gv2);
                gv3 = CH.KiemTraMGV(gv3);
                gv4 = CH.KiemTraMGV(gv4);
                gv5 = CH.KiemTraMGV(gv5);
                if (kiemTraGVTieuBan(gv1)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv1 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV1.requestFocus();
                } else if (kiemTraGVTieuBan(gv2)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv2 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV2.requestFocus();
                } else if (kiemTraGVTieuBan(gv3)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv3 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV3.requestFocus();
                } else if (kiemTraGVTieuBan(gv4)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv4 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV4.requestFocus();
                } else if (kiemTraGVTieuBan(gv5)) {
                    JOptionPane.showMessageDialog(this, "Giảng viên " + gv5 + " đã thuộc tiểu ban khác!!");
                    tabPCTB_tf_GV5.requestFocus();
                } else {
                    try {
                        Connection con = DBConnection.getConnection();
                        String sql = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv1 + "')";
                        String sql2 = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv2 + "')";
                        String sql3 = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv3 + "')";
                        String sql4 = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv4 + "')";
                        String sql5 = "insert into CT_TIEUBAN values ('" + matb + "' , '" + gv5 + "')";
                        PreparedStatement ps = con.prepareStatement(sql);
                        PreparedStatement ps2 = con.prepareStatement(sql2);
                        PreparedStatement ps3 = con.prepareStatement(sql3);
                        PreparedStatement ps4 = con.prepareStatement(sql4);
                        PreparedStatement ps5 = con.prepareStatement(sql5);
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới tài khoản", "Warning!", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            if (ps.executeUpdate() > 0) {
                                if (ps2.executeUpdate() > 0) {
                                    if (ps3.executeUpdate() > 0) {
                                        if (ps4.executeUpdate() > 0) {
                                            if (ps5.executeUpdate() > 0) {
                                                JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công các giáo viên cho tiểu ban " + matb);
                                                hienThiBangTieuBan();
                                                hienThiBangGVConLai();
                                                resetNhapPCTB();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SQLException | HeadlessException e) {
                        JOptionPane.showMessageDialog(this, "Giảng viên chưa được lưu!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã Giảng Viên Không Tồn Tại!!!");
                tabPCTB_tf_GV1.requestFocus();
            }
        }
    }//GEN-LAST:event_tabPCTB_btn_ThemMoiActionPerformed

    private void tabPCTB_btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCTB_btn_SuaActionPerformed
        //dispose();
//        this.setEnabled(false);
        SuaTB STB = new SuaTB();
        STB.setVisible(true);
        STB.setLocationRelativeTo(this);
    }//GEN-LAST:event_tabPCTB_btn_SuaActionPerformed

    public Boolean ktmatb_ctdoan(String maTB) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select * from ct_doan where matieuban=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maTB);
            ResultSet rs = ps.executeQuery();
            return rs.next(); //JOptionPane.showMessageDialog(this, "Mã tiểu ban chưa có giảng viên tồn tại");
        } catch (Exception e) {
        }
        return false;
    }

    private void xoaTB(String maTB) {
        try {
            Connection con = DBConnection.getConnection();
//            PreparedStatement pscham = con.prepareStatement("DELETE cham FROM cham t1 INNER JOIN ct_tieuban t2 ON t1.MAGV = t2.MAGV WHERE t2.MATIEUBAN='" + maTB + "'");
            PreparedStatement ps = con.prepareStatement("delete from ct_tieuban where matieuban='" + maTB + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa các giảng viên trong tiểu ban" + maTB, "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
//                if (pscham.executeUpdate() > 0) {
//                    System.out.println("Xóa gv trong bảng chấm");
//                }
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công chi tiết tiểu ban " + maTB);
                }
                hienThiBangGVConLai();
                hienThiBangTieuBan();
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Xóa chi tiết tiểu ban không thành công!!");
        }
    }
    private void tabPCTB_btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCTB_btn_XoaActionPerformed
        String maTB = tabPCTB_cb_MaTB.getSelectedItem().toString();
        System.out.println(maTB);
        if (!ktmatb_ctdoan(maTB)) {
            xoaTB(maTB);
        } else {
            JOptionPane.showMessageDialog(this, "Tiểu ban đã được phân công chấm điểm, không thể xóa!!");
        }

    }//GEN-LAST:event_tabPCTB_btn_XoaActionPerformed

    private void tabPCTB_btn_ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCTB_btn_ThoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát", "Warning!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            tab_Center.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabPCTB_btn_ThoatActionPerformed
    public static ArrayList<String> LoadDataToComboBox() {
        ArrayList<String> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        String sql = "SELECT matieuban FROM tieuban order by matieuban";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("matieuban"));
            }
        } catch (Exception e) {

        }
        return list;
    }
    private void tabPGVTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPGVTBActionPerformed
        hienThiBangTieuBan();
        hienThiBangGVConLai();
        tab_Center.setSelectedIndex(7);
        resetComboBoxTB();
        tabPCTB_cb_MaTB.removeAllItems();
        ArrayList<String> list = LoadDataToComboBox();
        for (String item : list) {
            tabPCTB_cb_MaTB.addItem(item);
        }
    }//GEN-LAST:event_tabPGVTBActionPerformed

    private void tabPCTB_cb_SLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCTB_cb_SLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabPCTB_cb_SLActionPerformed
    public void resetComboBoxTB() {
        tabPCTB_tf_GV4.setVisible(false);
        tabPCTB_tf_GV5.setVisible(false);
        tabPCTB_lb_GV4.setVisible(false);
        tabPCTB_lb_GV5.setVisible(false);
    }
    private void tabPCTB_cb_SLItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tabPCTB_cb_SLItemStateChanged
        int gt = tabPCTB_cb_SL.getSelectedIndex();
        if (gt == 0) {
            resetComboBoxTB();
        } else {
            tabPCTB_tf_GV4.setVisible(true);
            tabPCTB_tf_GV5.setVisible(true);
            tabPCTB_lb_GV4.setVisible(true);
            tabPCTB_lb_GV5.setVisible(true);
        }

    }//GEN-LAST:event_tabPCTB_cb_SLItemStateChanged

    private void tabTimKiem_tf_maSVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabTimKiem_tf_maSVKeyTyped
        if (tabTimKiem_tf_maSV.getText().length() >= 10 && !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
            getToolkit().beep();
            evt.consume();
        }

    }//GEN-LAST:event_tabTimKiem_tf_maSVKeyTyped

    private void tabPCTB_cb_MaTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabPCTB_cb_MaTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabPCTB_cb_MaTBActionPerformed

    private void tabQLGVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLGVActionPerformed
        tab_Center.setSelectedIndex(3);
    }//GEN-LAST:event_tabQLGVActionPerformed

//QUẢN LÝ GIẢNG VIÊN====================================================================================================================================//

    private void tabQLGV_btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLGV_btn_thoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát về trang chủ", "Warning!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            tab_Center.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabQLGV_btn_thoatActionPerformed
    private void lamMoiTabQLGV() {
        tabQLGV_tf_maGV.setText("");
        tabQLGV_tf_tenGV.setText("");
        tabQLGV_tf_maGV.requestFocus();
        hienThiBangQLGV();
    }

    private boolean tonTaiGV(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from giangvien where magv='" + maGV + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    private void themGV(String maGV, String tenGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into giangvien values ('" + maGV + "',N'" + tenGV + "')");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới giảng viên", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công giảng viên " + maGV);
                    lamMoiTabQLGV();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    private void suaGV(String maGV, String tenGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("update giangvien set tengv=N'" + tenGV + "' where magv='" + maGV + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tên giảng viên", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã sửa thành công giảng viên " + maGV);
                    lamMoiTabQLGV();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    private void xoaGV(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from giangvien where magv='" + maGV + "'");
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công giảng viên " + maGV);

            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    public static String ma_Hoa_MK(String s) {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT CONVERT(VARCHAR(32), HashBytes('MD5','" + s + "' ), 2) as md5";
        try {
            PreparedStatement p = con.prepareStatement(sql);
            ResultSet m = p.executeQuery();
            while (m.next()) {
                s = m.getString(1);
            }
            m.close();
            p.close();
        } catch (SQLException e) {
            System.err.println("Loi ma hoa mk");
        }
        return s;
    }

    private void themTaiKhoanGV(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement psgv = con.prepareStatement("insert into TAIKHOANGV values ('" + maGV + "','" + maGV + "')");
            PreparedStatement pstk = con.prepareStatement("insert into TAIKHOAN values ('" + maGV + "','" + ma_Hoa_MK("123456") + "','" + 1 + "')");
            if (pstk.executeUpdate() > 0) {
                System.out.println("Thêm tài khoản thành công");
            }
            if (psgv.executeUpdate() > 0) {
                System.out.println("Thêm thành công tài khoản giảng viên");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản GV không thành công");
        }
    }

    private String layMaTB(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_tieuban where magv='" + maGV + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("matieuban");
            }
        } catch (Exception e) {

        }
        return "";
    }

    private boolean tonTaiGVHD_CTDA(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pshd = con.prepareStatement("select * from ct_doan where magvhd='" + maGV + "'");
            ResultSet rs = pshd.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Không tồn giải gvhd trong ctda");
        }
        return false;
    }

    private boolean tonTaiGVPB_CTDA(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pspb = con.prepareStatement("select * from ct_doan where magvpb='" + maGV + "'");
            ResultSet rs = pspb.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Không tồn giải gvpb trong ctda");
        }
        return false;
    }

    private boolean tonTaiGVPB_CTTB(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pspb = con.prepareStatement("select * from ct_tieuban where magv='" + maGV + "'");
            ResultSet rs = pspb.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Không tồn tại trong ct_tieuban");
        }
        return false;
    }

    private void xoaTaiKhoanGV(String maGV) {
        try {
            Connection con = DBConnection.getConnection();
//            if (tonTaiGVHD_CTDA(maGV)) {
//                PreparedStatement psgvhd = con.prepareStatement("update ct_doan set magvhd=null where magvhd='" + maGV + "'");
//                if (psgvhd.executeUpdate() > 0) {
//                    System.out.println("Xóa gvhd trong ctda");
//                }
//            }
//            if (tonTaiGVPB_CTDA(maGV)) {
//                PreparedStatement psgvpb = con.prepareStatement("update ct_doan set magvpb=null where magvpb='" + maGV + "'");
//                if (psgvpb.executeUpdate() > 0) {
//                    System.out.println("Xóa gvpb trong ctda");
//                }
//            }
//            PreparedStatement pscham = con.prepareStatement("DELETE cham FROM cham t1 INNER JOIN ct_tieuban t2 ON t1.MAGV = t2.MAGV WHERE t2.MATIEUBAN='" + layMaTB(maGV) + "'");
//            PreparedStatement ps = con.prepareStatement("delete from ct_tieuban where matieuban='" + layMaTB(maGV) + "'");
            PreparedStatement psgv = con.prepareStatement("delete from TAIKHOANGV where magv='" + maGV + "' ");
            PreparedStatement pstk = con.prepareStatement("delete from TAIKHOAN where madangnhap='" + maGV + "'");
            //PreparedStatement pscham= con.prepareStatement("delete from CHAM where magv='"+maGV+"'");
            if (psgv.executeUpdate() > 0) {
                System.out.println("Xóa tài khoản GV thành công");
            }
//            if (pscham.executeUpdate() > 0) {
//                System.out.println("Xoa giảng viên trong bảng chấm");
//            }
            if (pstk.executeUpdate() > 0) {
                System.out.println("Xóa tài khoản thành công");
            }
//            if (ps.executeUpdate() > 0) {
//                System.out.println("Xóa tiểu ban chứa giảng viên " + maGV);
//            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Xóa giảng viên khỏi Tài Khoản giảng viên lỗi");
        }
    }
    private void tabQLGV_btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLGV_btn_themActionPerformed
        if (tabQLGV_tf_maGV.getText().isEmpty() || tabQLGV_tf_tenGV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        } else {
            ChuanHoa CH = new ChuanHoa();
            String maGV = CH.KiemTraMGV(tabQLGV_tf_maGV.getText());
            String tenGV = CH.ChuanHoaTen(tabQLGV_tf_tenGV.getText());
            if (!maGV.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabQLGV_tf_tenGV.getText())).equals("0")) {
                if (!tonTaiGV(maGV)) {
                    themGV(maGV, tenGV);
                    themTaiKhoanGV(maGV);
                } else {
                    JOptionPane.showMessageDialog(this, "Giảng viên đã tồn tại không thể thêm mới");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thông tin nhập không đúng");
            }

        }
    }//GEN-LAST:event_tabQLGV_btn_themActionPerformed

    private void tabQLGV_btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLGV_btn_suaActionPerformed
        if (tabQLGV_tf_maGV.getText().equals("") || tabQLGV_tf_tenGV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        } else {
            ChuanHoa CH = new ChuanHoa();
            String maGV = CH.KiemTraMGV(tabQLGV_tf_maGV.getText());
            String tenGV = CH.ChuanHoaTen(tabQLGV_tf_tenGV.getText());
            if (!maGV.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabQLGV_tf_tenGV.getText())).equals("0")) {
                if (tonTaiGV(maGV)) {
                    suaGV(maGV, tenGV);
                } else {
                    JOptionPane.showMessageDialog(this, "Giảng viên chưa tồn tại không thể sửa");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thông tin nhập không đúng");
            }
        }
    }//GEN-LAST:event_tabQLGV_btn_suaActionPerformed

    private void tabQLGV_btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLGV_btn_xoaActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String maGV = CH.KiemTraMGV(tabQLGV_tf_maGV.getText());
        if (tonTaiGV(maGV)) {
            if (!tonTaiGVHD_CTDA(maGV) && !tonTaiGVPB_CTDA(maGV) && !tonTaiGVPB_CTTB(maGV)) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa giảng viên", "Warning!", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    xoaTaiKhoanGV(maGV);
                    xoaGV(maGV);
                    lamMoiTabQLGV();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Giảng viên đã được phân công, không xóa được");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Giảng viên chưa có nên không xóa được");
        }
    }//GEN-LAST:event_tabQLGV_btn_xoaActionPerformed

    private void jcbbgvhdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbbgvhdActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jcbbgvhdActionPerformed

    private void tabQLSV_btn_ThemMoi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_ThemMoi2ActionPerformed
        String masv = jcbbmasv.getSelectedItem().toString().trim();
//        String magvhd = jcbbgvhd.getSelectedItem().toString();
//        String magvpb = jcbbgvpb.getSelectedItem().toString();
//        String matb = jcbbtb.getSelectedItem().toString();
        if ((tontaigvhd(masv))) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm giảng viên hướng dẫn?", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                luuphanconghd();
                loadPhanCong();
            }

        } else if (ktDKDiemHD(masv)) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm giảng viên phản biện?", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                luuphancongpb();
                loadPhanCong();
            }

        } else if ((ktDKDiemPB(masv) && !ktttTB(masv))) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm tiểu ban?", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                luuphancongtb();
                loadPhanCong();
                themcham(layTaiMaDA_CTDA(masv));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Sinh viên này đã có đầy đủ phân công", "Thông báo", JOptionPane.OK_OPTION);
        }
//        String matb1 = TimTieuBan(magvhd);
//        String matb2 = TimTieuBan(magvpb);
//        if (masv == "") {
//            JOptionPane.showMessageDialog(this, "Mã sinh viên không được để trống!", "Cảnh báo", JOptionPane.OK_OPTION);
//        }
//        if (!magvpb.equals(magvhd)) {
//            if (ThaoTacGV.ktMaSV(masv)) {
//                if (matb.equals(matb1) || matb.equals(matb2)) {
//                    JOptionPane.showMessageDialog(this, "Giáo viên hướng dẫn hoặc giáo viên phản biện đã thuộc thiểu ban!", "cảnh báo", JOptionPane.OK_OPTION);
//
//                } else {
//                    luuphancong(magvhd, magvpb, matb);
//                    JOptionPane.showMessageDialog(this, "Lưu thành công!", "Thông báo", JOptionPane.OK_OPTION);
//                    loadPhanCong();
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Sinh viên không tồn tại!", "cảnh báo", JOptionPane.OK_OPTION);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Mã giảng viên phản biện không được trùng với mã giảng viên hướng dẫn!", "cảnh báo", JOptionPane.OK_OPTION);
//        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tabQLSV_btn_ThemMoi2ActionPerformed
    public void themcham(String maDa) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO CHAM (MAGV,MADOAN) SELECT CT_TIEUBAN.MAGV, CT_DOAN.MADOAN FROM CT_TIEUBAN, CT_DOAN, (select MATIEUBAN FROM CT_DOAN WHERE MADOAN=?) AS A WHERE CT_TIEUBAN.MATIEUBAN=A.MATIEUBAN AND CT_DOAN.MADOAN=? ");
            preparedStatement1.setString(1, maDa);
            preparedStatement1.setString(2, maDa);
            preparedStatement1.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("lỗi thêm bảng chấm");
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean tonTaiMDA_CTDA(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select madoan from ct_doan where masv='" + maSV + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    private String layMATBCu(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement psmatb = con.prepareStatement("select matieuban from ct_doan where masv='" + maSV + "'");
            ResultSet rs = psmatb.executeQuery();
            while (rs.next()) {
                return rs.getString("matieuban");
            }
        } catch (Exception ex) {
        }
        return "";
    }
    private String layMADACu(String maSV) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement psmatb = con.prepareStatement("select madoan from ct_doan where masv='" + maSV + "'");
            ResultSet rs = psmatb.executeQuery();
            while (rs.next()) {
                return rs.getString("madoan");
            }
        } catch (Exception ex) {
        }
        return "";
    }
    public boolean ktttDiem(String maSV) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("select * from ct_doan where diemhd=0 and diempb is null and masv=? ");
            preparedStatement.setString(1, maSV);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Boolean ktdpb(String maSv) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("select * from ct_doan where diempb is null and masv=? ");
            preparedStatement.setString(1, maSv);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private void tabQLSV_btn_Sua2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_Sua2ActionPerformed
        String masv = jcbbmasv.getSelectedItem().toString().trim();
        if ((tontaigvhd(masv)) || (!tontaigvhd(masv) && !tontaimaDA(masv))) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa giảng viên hướng dẫn?", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                luuphanconghd();
                loadPhanCong();
            }

        } else if (ktDKDiemHD(masv) && ktdpb(masv)) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa giảng viên phản biện?", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                luuphancongpb();
                loadPhanCong();
            }

        } else if (ktDKDiemTB(masv)) {
            JOptionPane.showMessageDialog(this, "Sinh viên này đã có điểm tiểu ban, không thể sửa", "Thông báo", JOptionPane.OK_OPTION);
        } else if (ktDKDiemPB(masv) && !ktDKDiemTB(masv) && ktttTB(masv)) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tiểu ban?", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                luuphancongtb();
                loadPhanCong();
                xoact_doan_cham(masv);
                themcham(layTaiMaDA_CTDA(masv));
            }

        }

//        String magvhd = String.valueOf(jcbbgvhd.getSelectedItem().toString());
//        String magvpb = String.valueOf(jcbbgvpb.getSelectedItem().toString());
//        String matb = String.valueOf(jcbbtb.getSelectedItem().toString());
//        String matb1 = TimTieuBan(magvhd);
//        String matb2 = TimTieuBan(magvpb);
//        String matbcu= layMATBCu(masv);
//        if (masv.equals("")) {
//            JOptionPane.showMessageDialog(this, "Mã sinh viên không được để trống!", "Cảnh báo", JOptionPane.OK_OPTION);
//        }
//        if (!magvpb.equals(magvhd)) {
//            if (ThaoTacGV.ktMaSV(masv)) {
//                if (matb.equals(matb1) || matb.equals(matb2)) {
//                    JOptionPane.showMessageDialog(this, "Giáo viên hướng dẫn hoặc giáo viên phản biện đã thuộc thiểu ban!", "cảnh báo", JOptionPane.OK_OPTION);
//
//                } else {
//                    
//                        luuphancong(magvhd, magvpb, matb);
//                    if (tonTaiMDA_CTDA(masv)) {
//                        try {
//                            Connection con = DBConnection.getConnection();
//                            PreparedStatement psxoa= con.prepareStatement("DELETE cham FROM cham t1 INNER JOIN ct_tieuban t2 ON t1.MAGV = t2.MAGV WHERE t2.MATIEUBAN='" + matbcu + "'");
//                            if(psxoa.executeUpdate()>0){
//                                System.out.println("Xóa tb "+matb+ " khỏi bảng chấm");
//                            }
//                            PreparedStatement ps = con.prepareStatement("INSERT INTO CHAM (MAGV,MADOAN)\n"
//                                    + "SELECT CT_TIEUBAN.MAGV, CT_DOAN.MADOAN \n"
//                                    + "FROM CT_TIEUBAN, CT_DOAN, (select MATIEUBAN FROM CT_DOAN WHERE MASV=?) AS A \n"
//                                    + "WHERE CT_TIEUBAN.MATIEUBAN=A.MATIEUBAN AND CT_DOAN.MASV=? ");
//                            ps.setString(1, masv);
//                            ps.setString(2, masv);
//                            ps.executeUpdate();
//                        } catch (Exception e) {
//                            JOptionPane.showMessageDialog(this, "Lỗi thêm bảng chấm");
//                        }
//
//                    }
//                    JOptionPane.showMessageDialog(this, "Lưu thành công!", "Thông báo", JOptionPane.OK_OPTION);
//
//                    loadPhanCong();
//                    }
//                    
//                
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Sinh viên không tồn tại!", "cảnh báo", JOptionPane.OK_OPTION);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Mã giảng viên phản biện không được trùng với mã giảng viên hướng dẫn!", "cảnh báo", JOptionPane.OK_OPTION);
//        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tabQLSV_btn_Sua2ActionPerformed

//    public void xoaDiemvaTB(String maDA, Float diemHD, Float diemPB){
//        java.sql.Connection connection = DBConnection.getConnection();
//        try {
//
//            PreparedStatement preparedStatement = connection.prepareStatement("update CT_DOAN set CT_DOAN.MADOAN=?, CT_DOAN.DIEMHD=?, CT_DOAN.DIEMPB=? where CT_DOAN.MASV=?  ");
//            preparedStatement.setString(1, maDA);
//            preparedStatement.setFloat(2, diemHD);
//            preparedStatement.setFloat(3, diemPB);
//            preparedStatement.setString(4, jtfmasv.getText().toString().trim());
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public String laymatb_ctdoan(){
//        String matb="";
//        java.sql.Connection connection = DBConnection.getConnection();
//        try {
//
//            PreparedStatement preparedStatement = connection.prepareStatement("select matieuban from ct_doan where ct_doan.masv=?  ");
//            preparedStatement.setString(1, jtfmasv.getText().toString().trim());
//            ResultSet resultSet=preparedStatement.executeQuery();
//            while(resultSet.next()){
//                matb= resultSet.getString("MATIEUBAN");
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ThaoTacGV.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return matb;
//    }
    public void xoact_doan_cham(String maSV){
        java.sql.Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE cham FROM cham,(select madoan from ct_doan where masv='"+maSV+"') as a  where cham.madoan=a.madoan");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void tabQLSV_btn_Thoat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLSV_btn_Thoat2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabQLSV_btn_Thoat2ActionPerformed

    private void jComboBox_LopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_LopActionPerformed

        ThongKeSV();
    }//GEN-LAST:event_jComboBox_LopActionPerformed

    private void jComboBox_TieuBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_TieuBanActionPerformed

//        thongKeGV(jComboBox_TieuBan.getSelectedItem().toString());
        thongKeGV();
    }//GEN-LAST:event_jComboBox_TieuBanActionPerformed

    private void jComboBox_ThongkeDiemTheoLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ThongkeDiemTheoLopActionPerformed

        double g = gioi(), k = kha(), tb = trungBinh(), t = tong(), kx = khongxet(), r = (t - g - k - tb - kx);
        //System.out.println(tb);
        //bieuDoDiem bd = new bieuDoDiem();
        DefaultPieDataset pie = new DefaultPieDataset();
        pie.setValue("GIỎI", new Double(g));
        pie.setValue("KHÁ", new Double(k));
        pie.setValue("TRUNG BÌNH", new Double(tb));
        pie.setValue("RỚT", new Double(r));
        pie.setValue("KHÔNG XÉT", kx);

        JFreeChart pieChart = ChartFactory.createPieChart("Biểu đồ thống kê kết quả sinh viên lớp ".toUpperCase() + jComboBox_ThongkeDiemTheoLop.getSelectedItem(), pie, true, true, true);
        PiePlot p = (PiePlot) pieChart.getPlot();
        ChartPanel chartPanel = new ChartPanel(pieChart);

        jPanel_bieudo.removeAll();
        jPanel_bieudo.add(chartPanel);
        chartPanel.setSize(444, 555);
        jPanel_bieudo.validate();
        jPanel_bieudo.repaint();
    }//GEN-LAST:event_jComboBox_ThongkeDiemTheoLopActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        tab_Center.setSelectedIndex(9);
        jComboBox_Lop.removeAllItems();;
        loadMaComboBoxLop();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed

        tab_Center.setSelectedIndex(10);
        jComboBox_TieuBan.removeAllItems();
        loadTenTieuBan();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        tab_Center.setSelectedIndex(11);
        jComboBox_ThongkeDiemTheoLop.removeAllItems();
        loadTenComboBoxLop();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed

//HỘI ĐỒNG BÁO CÁO=====================================================================================================================================================================
    private void lamMoiTabQLHD() {
        tabQLHD_tf_maHD.setText("");
        tabQLHD_tf_tenHD.setText("");
        tabQLHD_tf_maHD.requestFocus();
        hienThiBangQLHD();
    }

    private boolean tonTaiHD(String maHD) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from HOIDONGBAOCAO where MAHDBC='" + maHD + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    private void themHD(String maHD, String tenHD) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into HOIDONGBAOCAO values ('" + maHD + "',N'" + tenHD + "')");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới hội đồng", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công hội đồng " + maHD);
                    lamMoiTabQLHD();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    private void suaHD(String maHD, String tenHD) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("update hoidongbaocao set tenhdbc=N'" + tenHD + "' where mahdbc='" + maHD + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tên hội đồng", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã sửa thành công hội đồng " + maHD);
                    lamMoiTabQLHD();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    public Boolean kt_hdbc_tb(String maHD) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pspb = con.prepareStatement("select * from tieuban where mahdbc='" + maHD + "'");
            ResultSet rs = pspb.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    private void xoaHD(String maHD) {
        try {
            Connection con = DBConnection.getConnection();
//            PreparedStatement pshdbc = con.prepareStatement("update tieuban set mahdbc=null where mahdbc='" + maHD + "'");
            PreparedStatement ps = con.prepareStatement("delete from hoidongbaocao where mahdbc='" + maHD + "'");
//            if (pshdbc.executeUpdate() > 0) {
//                System.out.println("Set null mahdbc bên tieuban");
//            }
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công hội đồng " + maHD);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn");
        }
    }

    private void tabQLHD_btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLHD_btn_suaActionPerformed
        if (tabQLHD_tf_maHD.getText().isEmpty() || tabQLHD_tf_tenHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        } else {
            ChuanHoa CH = new ChuanHoa();
            String maHD = CH.KiemTraMHD(tabQLHD_tf_maHD.getText());
            String tenHD = CH.ChuanHoaTen(tabQLHD_tf_tenHD.getText());
            if (!maHD.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabQLHD_tf_tenHD.getText())).equals("0")) {
                if (tonTaiHD(maHD)) {
                    suaHD(maHD, tenHD);
                } else {
                    JOptionPane.showMessageDialog(this, "Hội đồng chưa tồn tại không thể sửa");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thông tin nhập không đúng");
            }
        }
    }//GEN-LAST:event_tabQLHD_btn_suaActionPerformed

    private void tabQLHD_btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLHD_btn_xoaActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String maHD = CH.KiemTraMHD(tabQLHD_tf_maHD.getText());
        if (tonTaiHD(maHD) && !kt_hdbc_tb(maHD)) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa hội đồng", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                xoaHD(maHD);
                lamMoiTabQLHD();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hội đồng chưa có hoặc đã có tiểu ban nên không xóa được");
        }
    }//GEN-LAST:event_tabQLHD_btn_xoaActionPerformed

    private void tabQLHD_btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLHD_btn_thoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát về trang chủ", "Warning!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            tab_Center.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabQLHD_btn_thoatActionPerformed

    private void tabQLHD_btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLHD_btn_themActionPerformed
        if (tabQLHD_tf_maHD.getText().isEmpty() || tabQLHD_tf_tenHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        } else {
            ChuanHoa CH = new ChuanHoa();
            String maHD = CH.KiemTraMHD(tabQLHD_tf_maHD.getText());
            String tenHD = CH.ChuanHoaTen(tabQLHD_tf_tenHD.getText());
            if (!maHD.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabQLHD_tf_tenHD.getText())).equals("0")) {
                if (!tonTaiHD(maHD)) {
                    themHD(maHD, tenHD);
                } else {
                    JOptionPane.showMessageDialog(this, "Hội đồng đã tồn tại không thể thêm mới");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thông tin nhập không đúng");
            }
        }
    }//GEN-LAST:event_tabQLHD_btn_themActionPerformed

    private void tabQLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLHDActionPerformed
        tab_Center.setSelectedIndex(4);
        hienThiBangQLHD();
    }//GEN-LAST:event_tabQLHDActionPerformed

//QUẢN LÝ TIỂU BAN==================================================================================================================================================================//
    private void lamMoiTabQLTB() {
        tabQLHD_tf_maHD.setText("");
        tabQLHD_tf_tenHD.setText("");
        tabQLHD_tf_maHD.requestFocus();
        hienThiBangQLTB();
        //LoadDataToComboBoxQLTB();
    }

    public Boolean tontaitb_cttb(String maTB) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from CT_TIEUBAN where MATIEUBAN='" + maTB + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    private boolean tonTaiQLTB(String maTB) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from TIEUBAN where MATIEUBAN='" + maTB + "'");
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    private void themQLTB(String maTB, String tenTB, String maHD) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into TIEUBAN values ('" + maTB + "',N'" + tenTB + "','" + maHD + "')");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới tiểu ban", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công tiểu ban " + maTB);
                    lamMoiTabQLTB();
                }
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn thêm TB");
        }
    }

    private void suaQLTB(String maTB, String tenTB, String maHD) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("update TIEUBAN set tentieuban=N'" + tenTB + "', mahdbc='" + maHD + "' where matieuban='" + maTB + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tiểu ban", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã sửa thành công tiểu ban " + maTB);
                    lamMoiTabQLTB();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    private void xoaQLTB(String maTB) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pscham = con.prepareStatement("DELETE cham FROM cham t1 INNER JOIN ct_tieuban t2 ON t1.MAGV = t2.MAGV WHERE t2.MATIEUBAN='" + maTB + "'");
            PreparedStatement psctda = con.prepareStatement("update ct_doan set matieuban=null where matieuban='" + maTB + "'");
            PreparedStatement pscttb = con.prepareStatement("delete from CT_TIEUBAN where matieuban='" + maTB + "'");
            PreparedStatement ps = con.prepareStatement("delete from TIEUBAN where matieuban='" + maTB + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa tiểu ban", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (pscham.executeUpdate() > 0) {
                    System.out.println("Xóa tiểu ban trong chấm");
                }
                if (psctda.executeUpdate() > 0) {
                    System.out.println("Xóa tieuban trong ctdoan");
                }
                if (pscttb.executeUpdate() > 0) {
                    System.out.println("Xóa tieuban trong ct tieuban");
                }
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công tiểu ban " + maTB);
                    lamMoiTabQLTB();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    public void xuatComboBoxTB() {
        tabQLTB_cb_maHDBC.removeAllItems();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from hoidongbaocao ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tabQLTB_cb_maHDBC.addItem(rs.getString("mahdbc"));
            }
        } catch (Exception e) {

        }
    }
    private void tabQLTB_btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLTB_btn_suaActionPerformed
        if (tabQLTB_tf_maTB.getText().isEmpty() || tabQLTB_tf_tenTB.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        } else {
            ChuanHoa CH = new ChuanHoa();
            String maTB = CH.KiemTraMTB(tabQLTB_tf_maTB.getText().trim());
            String tenTB = CH.ChuanHoaTen(tabQLTB_tf_tenTB.getText().trim());
            String maHD = String.valueOf(tabQLTB_cb_maHDBC.getSelectedItem().toString().trim());
            if (!maTB.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabQLTB_tf_tenTB.getText())).equals("0")) {
                if (tonTaiQLTB(maTB)) {
                    suaQLTB(maTB, tenTB, maHD);
                } else {
                    JOptionPane.showMessageDialog(this, "Tiểu ban chưa có không thể sửa");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nhập lại thông tin");
            }
        }
    }//GEN-LAST:event_tabQLTB_btn_suaActionPerformed

    private void tabQLTB_btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLTB_btn_xoaActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String maTB = CH.KiemTraMTB(tabQLTB_tf_maTB.getText().trim());
        if (!maTB.equals("0")) {
            if (tonTaiQLTB(maTB) && !tontaitb_cttb(maTB)) {
                xoaQLTB(maTB);
            } else {
                JOptionPane.showMessageDialog(this, "Tiểu ban chưa có hoặc đã có thành viên, không thể xóa");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nhập lại thông tin");
        }
    }//GEN-LAST:event_tabQLTB_btn_xoaActionPerformed

    private void tabQLTB_btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLTB_btn_thoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát về trang chủ", "Warning!", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            tab_Center.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabQLTB_btn_thoatActionPerformed

    private void tabQLTB_btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLTB_btn_themActionPerformed
        if (tabQLTB_tf_maTB.getText().isEmpty() || tabQLTB_tf_tenTB.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
        } else {
            ChuanHoa CH = new ChuanHoa();
            String maTB = CH.KiemTraMTB(tabQLTB_tf_maTB.getText().trim());
            String tenTB = CH.ChuanHoaTen(tabQLTB_tf_tenTB.getText().trim());
            String maHD = String.valueOf(tabQLTB_cb_maHDBC.getSelectedItem().toString().trim());
            if (!maTB.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabQLTB_tf_tenTB.getText())).equals("0")) {
                if (!tonTaiQLTB(maTB)) {
                    themQLTB(maTB, tenTB, maHD);
                } else {
                    JOptionPane.showMessageDialog(this, "Tiểu ban đã tồn tại không thể thêm");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nhập lại thông tin");
            }
        }
    }//GEN-LAST:event_tabQLTB_btn_themActionPerformed

    private void tabQLTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLTBActionPerformed
        tab_Center.setSelectedIndex(5);
        hienThiBangQLTB();
        xuatComboBoxTB();
    }//GEN-LAST:event_tabQLTBActionPerformed

    private void tabQLND_tf_MaDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabQLND_tf_MaDNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabQLND_tf_MaDNActionPerformed

    private void table_QLGVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_QLGVMouseClicked
        int i = table_QLGV.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_QLGV.getModel();
        tabQLGV_tf_maGV.setText(model.getValueAt(i, 0).toString().trim());
        tabQLGV_tf_tenGV.setText(model.getValueAt(i, 1).toString().trim());
    }//GEN-LAST:event_table_QLGVMouseClicked

    private void table_QLHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_QLHDMouseClicked
        int i = table_QLHD.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_QLHD.getModel();
        tabQLHD_tf_maHD.setText(model.getValueAt(i, 0).toString().trim());
        tabQLHD_tf_tenHD.setText(model.getValueAt(i, 1).toString().trim());
    }//GEN-LAST:event_table_QLHDMouseClicked

    private void table_QLTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_QLTBMouseClicked
        int i = table_QLTB.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_QLTB.getModel();
        tabQLTB_tf_maTB.setText(model.getValueAt(i, 0).toString());
        tabQLTB_tf_tenTB.setText(model.getValueAt(i, 1).toString());
        //String maHDBC= model.getValueAt(i, 2).toString();
        //if(maHDBC.equals()) tabQLSV_cb_XetDA.setSelectedIndex(0);
        //else tabQLSV_cb_XetDA.setSelectedIndex(1);
    }//GEN-LAST:event_table_QLTBMouseClicked

    private void tabTimKiem_tf_maSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabTimKiem_tf_maSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabTimKiem_tf_maSVActionPerformed

    public Boolean tontaigvhd(String maSv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_doan where masv='" + maSv + "' and magvhd is null ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;
    }

    public Boolean tontaimaDA(String maSv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_doan where masv='" + maSv + "' and madoan is not null ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;
    }

    public static Boolean ktDKDiemHD(String maSv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_doan where masv='" + maSv + "' and diemhd>=5 and diempb is null ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;

    }

    public static Boolean ktDKDiemPB(String maSv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from ct_doan where masv='" + maSv + "' and diempb>=5 ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;

    }

    public static Boolean ktDKDiemTB(String maSv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from cham,(select  ct_doan.MADOAN from ct_doan where ct_doan.masv='" + maSv + "') AS a where cham.MADOAN=a.MADOAN and cham.DIEMTIEUBAN is not null ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;

    }

    public static Boolean ktttTB(String maSv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select MATIEUBAN from CT_DOAN where MASV='" + maSv + "' and matieuban is not null ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;

    }

    public String laygvhd(String maSv) {
        String ma = "";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select magvhd from ct_doan where masv='" + maSv + "' ");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ma = resultSet.getString("magvhd");
            }

        } catch (Exception e) {

        }
        return ma;
    }

    public String laygvpb(String maSv) {
        String ma = "";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select magvpb from ct_doan where masv='" + maSv + "' ");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ma = resultSet.getString("magvpb");
            }

        } catch (Exception e) {

        }
        return ma;
    }

    public Boolean ktgvtb(String maGv) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from CT_tieuban where MAGV='" + maGv + "' ");
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {

        }
        return false;
    }
    private void jcbbmasvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbbmasvActionPerformed
        String maSV = jcbbmasv.getSelectedItem().toString();
        jcbbtb.removeAllItems();
        loadcbbgvhd();
        loadcbbgvpb(maSV);
        if (!ktgvtb(laygvhd(maSV)) && !ktgvtb(laygvpb(maSV))) {
            loadMaTieuBan0();
        } else if (ktgvtb(laygvhd(maSV)) && !ktgvtb(laygvpb(maSV))) {
            loadMaTieuBan1(maSV);
        } else if (!ktgvtb(laygvhd(maSV)) && ktgvtb(laygvpb(maSV))) {
            loadMaTieuBan3(maSV);
        } else {
            loadMaTieuBan(maSV);
        }

        if ((tontaigvhd(maSV)) || (!tontaigvhd(maSV) && !tontaimaDA(maSV))) {
            jLabel27.setVisible(true);
            jcbbgvhd.setVisible(true);
            jLabel30.setVisible(false);
            jcbbgvpb.setVisible(false);
            jLabel31.setVisible(false);
            jcbbtb.setVisible(false);
        } else if (ktDKDiemHD(maSV)) {
            jLabel27.setVisible(false);
            jcbbgvhd.setVisible(false);
            jLabel30.setVisible(true);
            jcbbgvpb.setVisible(true);
            jLabel31.setVisible(false);
            jcbbtb.setVisible(false);
        } else if ((ktDKDiemPB(maSV) && !ktDKDiemTB(maSV)) || (ktDKDiemPB(maSV) && ktttTB(maSV))) {
            jLabel27.setVisible(false);
            jcbbgvhd.setVisible(false);
            jLabel30.setVisible(false);
            jcbbgvpb.setVisible(false);
            jLabel31.setVisible(true);
            jcbbtb.setVisible(true);
        } else {
            jLabel27.setVisible(false);
            jcbbgvhd.setVisible(false);
            jLabel30.setVisible(false);
            jcbbgvpb.setVisible(false);
            jLabel31.setVisible(false);
            jcbbtb.setVisible(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbbmasvActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Admin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_Tong;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel dddd1;
    private javax.swing.JComboBox<String> jComboBox_Lop;
    private javax.swing.JComboBox<String> jComboBox_ThongkeDiemTheoLop;
    private javax.swing.JComboBox<String> jComboBox_TieuBan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_bieudo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTable jTable_DS_SV_DOAN;
    private javax.swing.JTable jTable_GV;
    private javax.swing.JTable jTablephancong;
    private javax.swing.JComboBox<String> jcbbgvhd;
    private javax.swing.JComboBox<String> jcbbgvpb;
    private javax.swing.JComboBox<String> jcbbmasv;
    private javax.swing.JComboBox<String> jcbbtb;
    private javax.swing.JMenuItem menuItem_DangNhap;
    private javax.swing.JMenuItem menuItem_DangXuat;
    private javax.swing.JMenuItem menuItem_Exit;
    private javax.swing.JMenuItem menuItem_Quanly;
    private javax.swing.JPanel tabHome;
    private javax.swing.JMenuItem tabPCSV;
    private javax.swing.JButton tabPCTB_btn_Sua;
    private javax.swing.JButton tabPCTB_btn_ThemMoi;
    private javax.swing.JButton tabPCTB_btn_Thoat;
    private javax.swing.JButton tabPCTB_btn_Xoa;
    private javax.swing.JComboBox<String> tabPCTB_cb_MaTB;
    private javax.swing.JComboBox<String> tabPCTB_cb_SL;
    private javax.swing.JLabel tabPCTB_lb_GV1;
    private javax.swing.JLabel tabPCTB_lb_GV2;
    private javax.swing.JLabel tabPCTB_lb_GV3;
    private javax.swing.JLabel tabPCTB_lb_GV4;
    private javax.swing.JLabel tabPCTB_lb_GV5;
    private javax.swing.JTextField tabPCTB_tf_GV1;
    private javax.swing.JTextField tabPCTB_tf_GV2;
    private javax.swing.JTextField tabPCTB_tf_GV3;
    private javax.swing.JTextField tabPCTB_tf_GV4;
    private javax.swing.JTextField tabPCTB_tf_GV5;
    private javax.swing.JMenuItem tabPGVTB;
    private javax.swing.JPanel tabPanel_GiangVien;
    private javax.swing.JPanel tabPanel_HoiDong;
    private javax.swing.JPanel tabPanel_PhanCongSinhVien;
    private javax.swing.JPanel tabPanel_PhanGVTB;
    private javax.swing.JPanel tabPanel_QuanLy;
    private javax.swing.JPanel tabPanel_SinhVien;
    private javax.swing.JPanel tabPanel_ThongKeDiem;
    private javax.swing.JPanel tabPanel_ThongKeGV;
    private javax.swing.JPanel tabPanel_ThongKeSV;
    private javax.swing.JPanel tabPanel_TieuBan;
    private javax.swing.JPanel tabPanel_TimKiem;
    private javax.swing.JMenuItem tabQLGV;
    private javax.swing.JButton tabQLGV_btn_sua;
    private javax.swing.JButton tabQLGV_btn_them;
    private javax.swing.JButton tabQLGV_btn_thoat;
    private javax.swing.JButton tabQLGV_btn_xoa;
    private javax.swing.JTextField tabQLGV_tf_maGV;
    private javax.swing.JTextField tabQLGV_tf_tenGV;
    private javax.swing.JMenuItem tabQLHD;
    private javax.swing.JButton tabQLHD_btn_sua;
    private javax.swing.JButton tabQLHD_btn_them;
    private javax.swing.JButton tabQLHD_btn_thoat;
    private javax.swing.JButton tabQLHD_btn_xoa;
    private javax.swing.JTextField tabQLHD_tf_maHD;
    private javax.swing.JTextField tabQLHD_tf_tenHD;
    private javax.swing.JButton tabQLND_btn_ChinhSua;
    private javax.swing.JButton tabQLND_btn_Thoat;
    private javax.swing.JLabel tabQLND_lb_MK;
    private javax.swing.JLabel tabQLND_lb_maDN;
    private javax.swing.JTextField tabQLND_tf_MaDN;
    private javax.swing.JTextField tabQLND_tf_MatKhau;
    private javax.swing.JMenuItem tabQLSV;
    private javax.swing.JButton tabQLSV_btn_Sua;
    private javax.swing.JButton tabQLSV_btn_Sua2;
    private javax.swing.JButton tabQLSV_btn_ThemMoi;
    private javax.swing.JButton tabQLSV_btn_ThemMoi2;
    private javax.swing.JButton tabQLSV_btn_Thoat;
    private javax.swing.JButton tabQLSV_btn_Thoat2;
    private javax.swing.JButton tabQLSV_btn_Xoa;
    private javax.swing.JComboBox<String> tabQLSV_cb_XetDA;
    private javax.swing.JRadioButton tabQLSV_rdb_Nam;
    private javax.swing.JRadioButton tabQLSV_rdb_Nu;
    private javax.swing.JTextField tabQLSV_tf_Lop;
    private javax.swing.JTextField tabQLSV_tf_maSV;
    private javax.swing.JTextField tabQLSV_tf_tenSV;
    private javax.swing.JMenuItem tabQLTB;
    private javax.swing.JButton tabQLTB_btn_sua;
    private javax.swing.JButton tabQLTB_btn_them;
    private javax.swing.JButton tabQLTB_btn_thoat;
    private javax.swing.JButton tabQLTB_btn_xoa;
    private javax.swing.JComboBox<String> tabQLTB_cb_maHDBC;
    private javax.swing.JTextField tabQLTB_tf_maTB;
    private javax.swing.JTextField tabQLTB_tf_tenTB;
    private javax.swing.JMenuItem tabTiemKiem;
    private javax.swing.JTextField tabTimKiem_tf_maSV;
    private javax.swing.JTabbedPane tab_Center;
    private javax.swing.JTable table_PhanCongTieuBan;
    private javax.swing.JTable table_PhanCongTieuBan_GV;
    private javax.swing.JTable table_QLGV;
    private javax.swing.JTable table_QLHD;
    private javax.swing.JTable table_QLTB;
    private javax.swing.JTable table_QuanLyNguoiDung;
    private javax.swing.JTable table_QuanLySinhVien;
    private javax.swing.JTable table_TimKiem;
    // End of variables declaration//GEN-END:variables

    private String strupr(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
