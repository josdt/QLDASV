
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acer
 */
public class SuaTB extends javax.swing.JFrame {

    /**
     * Creates new form SuaTB
     */
    public SuaTB() {
        initComponents();
        cbb_MaTB.removeAllItems();;
        hienThiComboBox();
  
    }
    
    public void hienThiComboBox(){
        ArrayList<String> list= LoadDataToComboBox();
        for(String item: list){
            cbb_MaTB.addItem(item);
        }
    }
    public static ArrayList<String> LoadDataToComboBox(){
        ArrayList<String> list= new ArrayList<>();
        Connection con= DBConnection.getConnection();
        String sql= "SELECT DISTINCT matieuban FROM tieuban order by matieuban";
        try{
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("matieuban"));
            }
        }catch(Exception e){
            
        }
        return list;
    }
    public void hienThiBangTB(String MaTB){
        try{
            Connection con= DBConnection.getConnection();
            Statement ps= con.createStatement();
            ResultSet rs=ps.executeQuery("select * from CT_TIEUBAN where matieuban='"+MaTB+"'");
            DefaultTableModel tbModel=(DefaultTableModel)table_MaGV.getModel();
            tbModel.setRowCount(0);
            while(rs.next()){
                String maGV= rs.getString("magv");
                String bang[]= {maGV};
                tbModel.addRow(bang);
            }
        }catch(Exception ex){
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbb_MaTB = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_MaGV = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_MaGVMoi = new javax.swing.JTextField();
        tf_MaGVCu = new javax.swing.JTextField();
        btn_Thoat = new javax.swing.JButton();
        btn_Sua = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Mã Giảng Viên Mới:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, 30));

        cbb_MaTB.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbb_MaTB.setForeground(new java.awt.Color(0, 255, 255));
        cbb_MaTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_MaTBActionPerformed(evt);
            }
        });
        jPanel1.add(cbb_MaTB, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 141, 30));

        table_MaGV.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        table_MaGV.setForeground(new java.awt.Color(0, 153, 153));
        table_MaGV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Mã Giảng Viên"
            }
        ));
        jScrollPane1.setViewportView(table_MaGV);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 110, 110));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("CHÌNH SỬA GIẢNG VIÊN TRONG TIỂU BAN");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Mã Giảng Viên Cũ:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, -1, 30));

        tf_MaGVMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tf_MaGVMoi.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tf_MaGVMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 140, 30));

        tf_MaGVCu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tf_MaGVCu.setForeground(new java.awt.Color(0, 153, 153));
        jPanel1.add(tf_MaGVCu, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 140, 30));

        btn_Thoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Thoat.setForeground(new java.awt.Color(0, 153, 153));
        btn_Thoat.setText("Thoát");
        btn_Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Thoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, 110, 40));

        btn_Sua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Sua.setForeground(new java.awt.Color(0, 153, 153));
        btn_Sua.setText("Sửa");
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 110, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setText("Mã Tiểu Ban:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbb_MaTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_MaTBActionPerformed
        String MaTB= cbb_MaTB.getSelectedItem().toString();
        hienThiBangTB(MaTB);
    }//GEN-LAST:event_cbb_MaTBActionPerformed
    private void lamMoi(){
        cbb_MaTB.setSelectedIndex(0);
        String MaTB= cbb_MaTB.getSelectedItem().toString();
        hienThiBangTB(MaTB);
        tf_MaGVCu.setText("");
        tf_MaGVMoi.setText("");
        tf_MaGVCu.requestFocus();
    }
    private boolean kiemTraTonTaiGV(String maGV){
        try{
            Connection con= DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement("select * from CT_TIEUBAN where magv='"+maGV+"'");
            ResultSet rs= ps.executeQuery();
            return rs.next();
        }catch(Exception e){
        }
        return false;
    }
    private void capNhatTieuBan(String maGVCu,String maGVMoi){
        try{
            Connection con= DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement("update CT_TIEUBAN set magv='"+maGVMoi+"' where magv='"+maGVCu+"'");
            int dialogResult= JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn sửa tiểu ban","Warning!",JOptionPane.YES_NO_OPTION);
                        if(dialogResult==JOptionPane.YES_OPTION){
                            if(ps.executeUpdate()>0){
                                JOptionPane.showMessageDialog(null, "Bạn đã sửa thành công!!");
                                lamMoi();
                            }
                        }
        }catch(SQLException | HeadlessException e){
        }
    }
    private void capNhatTieuBan_CHAM(String maGVCu,String maGVMoi){
       Double diem=0.0;
        try{
            Connection con= DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement("update CHAM set magv='"+maGVMoi+"', diemtieuban= ?  where magv='"+maGVCu+"'");
            ps.setDouble(1, diem);
            ps.executeUpdate();
                        
        }catch(SQLException | HeadlessException e){
            System.out.println("lỗi rồi nhá");
        }
    }
    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
        ChuanHoa CH= new ChuanHoa();
        String MaGV_Cu = CH.KiemTraMGV(tf_MaGVCu.getText());
        String MaGV_Moi= CH.KiemTraMGV(tf_MaGVMoi.getText());
        if(!MaGV_Cu.equals("0") && !MaGV_Moi.equals("0")){
            if(!MaGV_Cu.equals(MaGV_Moi)){
                if(kiemTraTonTaiGV(MaGV_Cu)){
                    if(kiemTraTonTaiGV(MaGV_Moi)){
                        JOptionPane.showMessageDialog(this, "Giảng viên đã thuộc tiểu ban khác không thể sửa!");
                    } else{
                        capNhatTieuBan(MaGV_Cu, MaGV_Moi);
                        capNhatTieuBan_CHAM(MaGV_Cu, MaGV_Moi);
                        
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã Giảng Viên Cũ Và Mới Không Được Giống Nhau!!");
            }
        } else{
            JOptionPane.showMessageDialog(this, "Mã Giảng Viên Không Đúng");
            tf_MaGVCu.requestFocus();
        }
    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThoatActionPerformed
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn thoát", "Warning!", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                dispose();
            }
    }//GEN-LAST:event_btn_ThoatActionPerformed

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
            java.util.logging.Logger.getLogger(SuaTB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuaTB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuaTB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuaTB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuaTB().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Sua;
    private javax.swing.JButton btn_Thoat;
    private javax.swing.JComboBox<String> cbb_MaTB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_MaGV;
    private javax.swing.JTextField tf_MaGVCu;
    private javax.swing.JTextField tf_MaGVMoi;
    // End of variables declaration//GEN-END:variables
}