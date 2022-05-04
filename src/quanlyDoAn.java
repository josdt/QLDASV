
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class quanlyDoAn extends javax.swing.JPanel {

    /**
     * Creates new form quanlyDoAn
     */
    public quanlyDoAn() {
        initComponents();
        
        hienThiBangDoAn();
    }
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

    public void themDA(String maDA, String tenDA) {
        String magv=GiangVien_Form.ma;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("insert into DOAN values ('" + maDA + "',N'" + tenDA + "','"+magv.toUpperCase()+"')");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thêm mới đồ án", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã thêm thành công đồ án " + maDA);
                    hienThiBangDoAn();
                    lamMoiTabDA();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    public void suaDA(String maDA, String tenDA) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("update DOAN set tendoan=N'" + tenDA + "' where madoan='" + maDA + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tên đồ án", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã sửa thành công đồ án " + maDA);
                    hienThiBangDoAn();
                    lamMoiTabDA();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }

    private void xoaDA(String maDA) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from DOAN where madoan='" + maDA + "'");
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa đồ án", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Bạn đã xóa thành công đồ án " + maDA);
                    hienThiBangDoAn();
                    lamMoiTabDA();
                }
            }
        } catch (SQLException | HeadlessException e) {
        }
    }
    public void hienThiBangDoAn() {
        try {
            String url = "jdbc:sqlserver://;databaseName=QLDASV;user=sa;password=123";
            Connection con = DriverManager.getConnection(url);
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select * from doan");
            DefaultTableModel tbModel = (DefaultTableModel) table_DanhMucDoAn.getModel();
            tbModel.setRowCount(0);
            while (rs.next()) {
                String maDA = rs.getString("madoan");
                //String tenDN=rs.getString("tendangnhap");
                String tenDA = rs.getString("tendoan");
                String maGV= rs.getString("magv");
                String bang[] = {maDA, tenDA,maGV};
                tbModel.addRow(bang);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    private void lamMoiTabDA() {
        tabDA_tf_MaDA.setText("");
        tabDA_tf_TenDA.setText("");
        tabDA_tf_MaDA.requestFocus();

    }
    public Boolean kttt_madoan(String maDA){
        try{
            Connection con= DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement("select * from ct_doan where madoan='"+maDA+"'");
            ResultSet rs= ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            
        }
        return false;
    }
    public String laymagv(String maDA){
        String magv="";
        try{
            Connection con= DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement("select magv from doan where madoan='"+maDA+"'");
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                magv=rs.getString("magv");
            }
        }catch(Exception e){
            
        }
        return magv;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_DanhMucDoAn = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        tabDA_tf_TenDA = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        tabDA_btn_ChinhSua = new javax.swing.JButton();
        tabDA_btn_Them = new javax.swing.JButton();
        tabDA_btn_Xoa = new javax.swing.JButton();
        tabDA_tf_MaDA = new javax.swing.JTextField();

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_DanhMucDoAn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table_DanhMucDoAn.setForeground(new java.awt.Color(0, 153, 153));
        table_DanhMucDoAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đồ Án", "Tên Đồ Án", "Mã Giảng Viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_DanhMucDoAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_DanhMucDoAnMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_DanhMucDoAn);

        jPanel10.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 510, 540));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 153, 153));
        jLabel22.setText("Mã Đồ Án");
        jPanel10.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, 30));

        tabDA_tf_TenDA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabDA_tf_TenDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabDA_tf_TenDAActionPerformed(evt);
            }
        });
        jPanel10.add(tabDA_tf_TenDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 300, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 153, 153));
        jLabel23.setText("Tên Đồ Án");
        jPanel10.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, -1, 30));

        tabDA_btn_ChinhSua.setBackground(new java.awt.Color(255, 255, 255));
        tabDA_btn_ChinhSua.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabDA_btn_ChinhSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_edit_50px.png"))); // NOI18N
        tabDA_btn_ChinhSua.setText("Chỉnh sửa");
        tabDA_btn_ChinhSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabDA_btn_ChinhSuaActionPerformed(evt);
            }
        });
        jPanel10.add(tabDA_btn_ChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 480, 160, -1));

        tabDA_btn_Them.setBackground(new java.awt.Color(255, 255, 255));
        tabDA_btn_Them.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabDA_btn_Them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_add_50px.png"))); // NOI18N
        tabDA_btn_Them.setText("Thêm mới ");
        tabDA_btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabDA_btn_ThemActionPerformed(evt);
            }
        });
        jPanel10.add(tabDA_btn_Them, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, -1, -1));

        tabDA_btn_Xoa.setBackground(new java.awt.Color(255, 255, 255));
        tabDA_btn_Xoa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabDA_btn_Xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_recycle_50px.png"))); // NOI18N
        tabDA_btn_Xoa.setText("Xóa bỏ");
        tabDA_btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabDA_btn_XoaActionPerformed(evt);
            }
        });
        jPanel10.add(tabDA_btn_Xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 590, 160, -1));

        tabDA_tf_MaDA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabDA_tf_MaDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabDA_tf_MaDAActionPerformed(evt);
            }
        });
        jPanel10.add(tabDA_tf_MaDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 300, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1340, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 1340, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void table_DanhMucDoAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_DanhMucDoAnMouseClicked
        int i = table_DanhMucDoAn.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) table_DanhMucDoAn.getModel();
        tabDA_tf_MaDA.setText(model.getValueAt(i, 0).toString().trim());
        tabDA_tf_TenDA.setText(model.getValueAt(i, 1).toString().trim());
    }//GEN-LAST:event_table_DanhMucDoAnMouseClicked

    private void tabDA_tf_TenDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabDA_tf_TenDAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabDA_tf_TenDAActionPerformed

    private void tabDA_btn_ChinhSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabDA_btn_ChinhSuaActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String magv=GiangVien_Form.ma.toUpperCase().trim();
        String maDA = CH.KiemTraMDA(tabDA_tf_MaDA.getText());
        String tenDA = CH.ChuanHoaTen(tabDA_tf_TenDA.getText());
        if (!maDA.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabDA_tf_TenDA.getText())).equals("0")) {
            if(magv.equals(laymagv(maDA).trim())){
                if (tonTaiMaDA(maDA)) {
                suaDA(maDA, tenDA);
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Đồ án chưa có, không thể sửa");
                    tabDA_tf_MaDA.requestFocus();
                }
            }
            else{
            JOptionPane.showMessageDialog(this, "Đồ án của giảng viên khác, không thể sửa");
            tabDA_tf_MaDA.requestFocus();
            }           
        } else {
            JOptionPane.showMessageDialog(this, "Nhập lại thông tin");
        }
    }//GEN-LAST:event_tabDA_btn_ChinhSuaActionPerformed

    private void tabDA_btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabDA_btn_ThemActionPerformed
        ChuanHoa CH = new ChuanHoa();
        String maDA = CH.KiemTraMDA(tabDA_tf_MaDA.getText());
        String tenDA = CH.ChuanHoaTen(tabDA_tf_TenDA.getText());
        if (!maDA.equals("0") && !CH.KiemTraTen(ChuanHoa.removeAccent(tabDA_tf_TenDA.getText())).equals("0")) {
            if (!tonTaiMaDA(maDA)) {
                themDA(maDA, tenDA);
            } else {
                JOptionPane.showMessageDialog(this, "Đồ án đã tồn tại không thể thêm mới");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nhập lại thông tin!!!!");
        }
    }//GEN-LAST:event_tabDA_btn_ThemActionPerformed

    private void tabDA_btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabDA_btn_XoaActionPerformed
        String magv=GiangVien_Form.ma.toUpperCase().trim();
        String maDA = tabDA_tf_MaDA.getText();
        System.out.println(magv);
        if(magv.equals(laymagv(maDA).trim())){
            if (tonTaiMaDA(maDA) && !kttt_madoan(maDA) ) {
            xoaDA(maDA);
        } else {
            JOptionPane.showMessageDialog(this, "Mã đồ án không tồn tại hoặc đã được phân cho sinh viên, không thể xóa");
            tabDA_tf_MaDA.requestFocus();
        }
        }
        else{
            JOptionPane.showMessageDialog(this, "Đồ án của giảng viên khác, không thể xóa");
            tabDA_tf_MaDA.requestFocus();
        }
        
    }//GEN-LAST:event_tabDA_btn_XoaActionPerformed

    private void tabDA_tf_MaDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabDA_tf_MaDAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabDA_tf_MaDAActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton tabDA_btn_ChinhSua;
    private javax.swing.JButton tabDA_btn_Them;
    private javax.swing.JButton tabDA_btn_Xoa;
    private javax.swing.JTextField tabDA_tf_MaDA;
    private javax.swing.JTextField tabDA_tf_TenDA;
    private javax.swing.JTable table_DanhMucDoAn;
    // End of variables declaration//GEN-END:variables
}
