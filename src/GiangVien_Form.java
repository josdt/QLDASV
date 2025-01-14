
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class GiangVien_Form extends javax.swing.JFrame {
    public static String ma="";
    /**
     * Creates new form GiangVien
     */

    
    public GiangVien_Form() {
    initComponents();     
    ArrayList<TaskItem> arrayList=new ArrayList<>();
        arrayList.add(new TaskItem("DIEMHUONGDAN", jLabelDiemHD));
        arrayList.add(new TaskItem("DIEMPHANBIEN", jLabelDiemPB));
        arrayList.add(new TaskItem("DIEMTIEUBAN", jLabelDiemTieuBan)); 
        arrayList.add(new TaskItem("QLDOAN", jLabelQLDoAn)); 
        arrayList.add(new TaskItem("PHANDOAN", jLabelPhanDoAn));  
        ConvertPanel convertPanel=new ConvertPanel(jPanelMain);
        convertPanel.setEvent(arrayList);

    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelPhanDoAn = new javax.swing.JLabel();
        jLabelDiemHD = new javax.swing.JLabel();
        jLabelDiemPB = new javax.swing.JLabel();
        jLabelDiemTieuBan = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelQLDoAn = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1366, Short.MAX_VALUE)
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
        );

        jLabelPhanDoAn.setBackground(new java.awt.Color(204, 255, 255));
        jLabelPhanDoAn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelPhanDoAn.setForeground(new java.awt.Color(0, 153, 153));
        jLabelPhanDoAn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPhanDoAn.setText("Phân Đồ Án");
        jLabelPhanDoAn.setOpaque(true);

        jLabelDiemHD.setBackground(new java.awt.Color(204, 255, 255));
        jLabelDiemHD.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDiemHD.setForeground(new java.awt.Color(0, 153, 153));
        jLabelDiemHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDiemHD.setText("Điểm Hướng Dẫn");
        jLabelDiemHD.setOpaque(true);

        jLabelDiemPB.setBackground(new java.awt.Color(204, 255, 255));
        jLabelDiemPB.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDiemPB.setForeground(new java.awt.Color(0, 153, 153));
        jLabelDiemPB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDiemPB.setText("Điểm Phản Biện");
        jLabelDiemPB.setOpaque(true);

        jLabelDiemTieuBan.setBackground(new java.awt.Color(204, 255, 255));
        jLabelDiemTieuBan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelDiemTieuBan.setForeground(new java.awt.Color(0, 153, 153));
        jLabelDiemTieuBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDiemTieuBan.setText("Điểm Tiểu Ban");
        jLabelDiemTieuBan.setOpaque(true);

        jLabel1.setBackground(new java.awt.Color(204, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đăng Xuất");
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabelQLDoAn.setBackground(new java.awt.Color(204, 255, 255));
        jLabelQLDoAn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelQLDoAn.setForeground(new java.awt.Color(0, 153, 153));
        jLabelQLDoAn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelQLDoAn.setText("Quản lý Đồ Án");
        jLabelQLDoAn.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabelDiemHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDiemPB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDiemTieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelQLDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPhanDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 546, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPhanDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDiemPB, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDiemTieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelQLDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 26, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setForeground(new java.awt.Color(0, 153, 153));
        jMenu1.setText("Hệ Thống");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItem1.setText("Đổi Mật Khẩu");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
        Login lg= new Login();
        lg.setVisible(true); 
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
            DoiMKGV a=new DoiMKGV();
            a.setTitle("Đổi mật khẩu người dùng");
            a.setLocation(700, 400);
            a.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(GiangVien_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiangVien_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiangVien_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiangVien_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiangVien_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDiemHD;
    private javax.swing.JLabel jLabelDiemPB;
    private javax.swing.JLabel jLabelDiemTieuBan;
    private javax.swing.JLabel jLabelPhanDoAn;
    private javax.swing.JLabel jLabelQLDoAn;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMain;
    // End of variables declaration//GEN-END:variables
}
