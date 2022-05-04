
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HP
 */
public class SinhVien_Form extends javax.swing.JFrame {
    
    public static String ma = "";

    /**
     * Creates new form SinhVien
     */
    public SinhVien_Form() {
        initComponents();

        layTT(ma);
        laydiemtieuban(ma);
    }
    
    public void layTT(String masv) {
        Connection ketNoi = DBConnection.getConnection();
        String sql = "select  tensv, lop, gioitinh\n"
                + "from sinhvien\n"
                + "where sinhvien.masv=?";
        String sql1 = "select madoan from ct_doan, sinhvien where ct_doan.masv=? and ct_doan.masv=sinhvien.masv and xetdoan=1";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(1, masv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //jLabel_masv.setText(rs.getString(1));
                jLabel_tensv.setText(rs.getString(1).toUpperCase());
                jLabel_lop.setText(rs.getString(2).toUpperCase());
                jLabel_gioitinh.setText(rs.getString(3).toUpperCase());
                
            }
            rs.close();
            ps.close();
            //ketNoi.close();

        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql1);
            ps.setString(1, masv);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                if (rs.getObject("madoan") == null) {
                    jLabel_madoan.setText("chưa có mã đồ án tốt nghiệp");
                } else {
                    jLabel_madoan.setText(rs.getString(1).toUpperCase());
                }
            } else {
                jLabel_madoan.setText("Không được làm đồ án tốt nghiệp");
            }
            rs.close();
            ps.close();
            ketNoi.close();
            
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public String layDiemhd(String madoan) {
        Connection ketNoi = DBConnection.getConnection();
        
        String diemhd = "";
        
        String sql = "select diemhd from ct_doan where masv =?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            
            ps.setString(1, madoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getObject("diemhd") == null) {
                    diemhd = null;
                } else {
                    diemhd = rs.getString(1);
                }
                
            }
        } catch (SQLException e) {
            System.err.println("lỗi lấy điểm hướng dẫn");
        }
        System.out.println(diemhd);
        return diemhd;
    }
    
    public String layDiempb(String madoan) {
        Connection ketNoi = DBConnection.getConnection();
        
        String diempb = "";
        
        String sql = "select diempb from ct_doan where masv =?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            
            ps.setString(1, madoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getObject("diempb") == null) {
                    diempb = null;
                } else {
                    diempb = rs.getString(1);
                }
                
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("diem pb" + diempb);
        return diempb;
    }
    
    public int layso(String madoan) {
        Connection ketNoi = DBConnection.getConnection();
        
        int dem = 0;
        
        String sql = " select count(magv) as dem  from cham, (select madoan from ct_doan where masv=?) as a WHERE CHAM.madoan=a.madoan";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            
            ps.setString(1, madoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dem = rs.getInt("dem");
                
            }
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("DEM" + dem);
        return dem;
    }
    
    public void laydiemtieuban(String madoan) {
        Connection ketNoi = DBConnection.getConnection();
        
        Vector vt = new Vector();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setNumRows(0);
        String sql = "select diemtieuban from cham, (select madoan from ct_doan where masv=?) as a WHERE CHAM.madoan=a.madoan order by diemtieuban";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            float diem = 0;
            ps.setString(1, madoan);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                if (rs.getObject("diemtieuban") == null) {
                    {
                        vt.add(layDiemhd(madoan));
                        vt.add(layDiempb(madoan));
                        System.out.println(rs.getString(1));
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        dtm.addRow(vt);
                        //break;
                    }
                } else if (rs.getFloat(1) < 4) {
                    vt.add(layDiemhd(madoan));
                    vt.add(layDiempb(madoan));
                    vt.add("0.0");
                    vt.add((Float.parseFloat(layDiemhd(madoan)) + Float.parseFloat(layDiempb(madoan))) / 3);
                    vt.add("RỚT");
                    dtm.addRow(vt);
                    //break;
                } else {
                    String sql2 = "select avg(diemtieuban) as diemtieuban from (select diemtieuban from cham, (select madoan from ct_doan where masv=?) as a WHERE CHAM.madoan=a.madoan)as a";
                    try {
                        PreparedStatement ps2 = ketNoi.prepareStatement(sql2);
                        ps2.setString(1, madoan);
                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            vt.add(layDiemhd(madoan));
                            vt.add(layDiempb(madoan));
                            
                            Float diemTrungBinh = ((Float.parseFloat(layDiemhd(madoan)) + Float.parseFloat(layDiempb(madoan))) + rs2.getFloat(1)) / 3;
                            Float diemtieuban = rs2.getFloat(1);
                            vt.add(diemtieuban);
                            vt.add(diemTrungBinh);
                            if (diemTrungBinh > 8 && diemtieuban > 4) {
                                vt.add("GIỎI");
                            }
                            if (diemTrungBinh > 6.5 && diemtieuban > 4) {
                                vt.add("KHÁ");
                            }
                            if (diemTrungBinh > 4 && diemtieuban > 4) {
                                vt.add("TRUNG BÌNH");
                            } else {
                                vt.add("RỚT");
                            }
                            
                        }
                        dtm.addRow(vt);
                        rs2.close();
                        ps2.close();
                        
                    } catch (SQLException e) {
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
                    }
                    //break;
                }
            } else if (!rs.next()) {
                if (layDiempb(madoan) == null) {
                    if (layDiemhd(madoan) == null) {
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        dtm.addRow(vt);
                    } else if (Float.parseFloat(layDiemhd(madoan)) < 4) {
                        vt.add(layDiemhd(madoan));
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        vt.add("RỚT");
                        dtm.addRow(vt);
                    } else {
                        vt.add(layDiemhd(madoan));
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        vt.add("");
                        dtm.addRow(vt);
                    }
                } else if (Float.parseFloat(layDiempb(madoan)) < 4) {
                    vt.add(layDiemhd(madoan));
                    vt.add("");
                    vt.add("");
                    vt.add("");
                    vt.add("RỚT");
                    dtm.addRow(vt);
                } else {
                    vt.add(layDiemhd(madoan));
                    vt.add(layDiempb(madoan));
                    vt.add("");
                    vt.add("");
                    vt.add("");
                    dtm.addRow(vt);
                }
            }
            ketNoi.close();
            rs.close();
            ps.close();
        } catch (SQLException e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    public void layDiem(String masv, String madoan) {
        
        Connection ketNoi = DBConnection.getConnection();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setNumRows(0);
        
        String sql = "select  ct_doan.diemhd,ct_doan.diempb,diemtieuban, round((ct_doan.diemhd+ct_doan.diempb+diemtieuban)/3,2)as diemTB from ct_doan, (\n"
                + "select  round(avg(diemtieuban),2) as diemtieuban\n"
                + "from  cham where madoan=? )as a \n"
                + "where ct_doan.masv=?";
        try {
            PreparedStatement ps = ketNoi.prepareStatement(sql);
            ps.setString(2, masv);
            ps.setString(1, madoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vector vt = new Vector();
                if (rs.getObject("diempb") == null) {
                    vt.add(rs.getString(1));
                    vt.add("0.0");
                } else {
                    vt.add(rs.getString(1));
                    
                    vt.add(rs.getString(2));
                }
                if (rs.getObject("diemtieuban") == null) {
                    
                    vt.add("0.0");
                    vt.add("0.0");
                    vt.add("RỚT");
                } else {
                    vt.add(rs.getString(3));
                    vt.add(rs.getString(4));
                    float diemTieuBan = Float.parseFloat(rs.getString(3).toString());
                    float diemTB = Float.parseFloat(rs.getString(4).toString());
                    if (diemTB >= 8 && diemTieuBan >= 5) {
                        vt.add("GIỎI");
                    } else if (diemTB >= 6.5 && diemTieuBan >= 5) {
                        vt.add("KHÁ");
                        
                    } else if (diemTB >= 5 && diemTieuBan >= 5) {
                        vt.add("TRUNG BÌNH");
                    } else {
                        vt.add("RỚT");
                    }
                }
                dtm.addRow(vt);
            } else {
                System.out.println("SinhVien_Form.layDiem()");
            }
            rs.close();
            ps.close();
            ketNoi.close();
        } catch (SQLException e) {
            System.err.println("loi!11");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel_tensv = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_lop = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel_gioitinh = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel_madoan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel_masv = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuItem_DangNhap = new javax.swing.JMenuItem();
        menuItem_DoiMK = new javax.swing.JMenuItem();
        menuItem_DangXuat = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItem_Exit = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setForeground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new java.awt.GridLayout(4, 2));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HỌ TÊN");
        jPanel2.add(jLabel1);

        jLabel_tensv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_tensv.setForeground(new java.awt.Color(0, 102, 102));
        jLabel_tensv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel_tensv);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LỚP");
        jPanel2.add(jLabel2);

        jLabel_lop.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_lop.setForeground(new java.awt.Color(0, 102, 102));
        jLabel_lop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel_lop);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("GIỚI TÍNH");
        jPanel2.add(jLabel8);

        jLabel_gioitinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_gioitinh.setForeground(new java.awt.Color(0, 102, 102));
        jLabel_gioitinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel_gioitinh);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MÃ ĐỒ ÁN");
        jPanel2.add(jLabel10);

        jLabel_madoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_madoan.setForeground(new java.awt.Color(0, 102, 102));
        jLabel_madoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel_madoan);

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ĐIỂM HƯỚNG DẪN", "ĐIỂM PHẢN BIỆN", "ĐIỂM TIỂU BAN", "ĐIỂM TRUNG BÌNH", "XẾP LOẠI"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel_masv.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_masv.setForeground(new java.awt.Color(255, 0, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("XIN CHÀO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_masv, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_masv, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addGap(277, 277, 277))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(340, 340, 340))))
        );

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

        menuItem_DoiMK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        menuItem_DoiMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_manager_16px.png"))); // NOI18N
        menuItem_DoiMK.setText("Đổi mật khẩu");
        menuItem_DoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_DoiMKActionPerformed(evt);
            }
        });
        jMenu2.add(menuItem_DoiMK);

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItem_DangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItem_DangNhapMouseClicked

    }//GEN-LAST:event_menuItem_DangNhapMouseClicked

    private void menuItem_DangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_DangNhapActionPerformed

    }//GEN-LAST:event_menuItem_DangNhapActionPerformed

    private void menuItem_DangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_DangXuatActionPerformed
        dispose();
        Login lg = new Login();
        lg.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_menuItem_DangXuatActionPerformed

    private void menuItem_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItem_ExitMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItem_ExitMouseClicked

    private void menuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItem_ExitActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void menuItem_DoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_DoiMKActionPerformed
        DoiMKSV a = new DoiMKSV();
        a.setTitle("Đổi mật khẩu người dùng");
        a.setLocationRelativeTo(this);
        a.setVisible(true);
    }//GEN-LAST:event_menuItem_DoiMKActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SinhVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SinhVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SinhVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SinhVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SinhVien_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_gioitinh;
    private javax.swing.JLabel jLabel_lop;
    private javax.swing.JLabel jLabel_madoan;
    public javax.swing.JLabel jLabel_masv;
    private javax.swing.JLabel jLabel_tensv;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem menuItem_DangNhap;
    private javax.swing.JMenuItem menuItem_DangXuat;
    private javax.swing.JMenuItem menuItem_DoiMK;
    private javax.swing.JMenuItem menuItem_Exit;
    // End of variables declaration//GEN-END:variables
}
