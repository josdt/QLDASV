/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Administrator
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Admin
 */
public class ConvertPanel {

    private String statusSelected = "";
    private JPanel jpnMain;

    public ConvertPanel(JPanel jpnMain) {
        this.jpnMain = jpnMain;
    }

//    public void setView(JLabel jlbItem) {
//        statusSelected = "TrangChu";
//        jlbItem.setBackground(new Color(26, 198, 255));
//        jpnMain.removeAll();
//        jpnMain.setLayout(new BorderLayout());
//        jpnMain.add(new HomJPanel());
//        jpnMain.validate();
//        jpnMain.repaint();
//   }

    public void setEvent(ArrayList<TaskItem> listTask) {
        for (TaskItem taskItem : listTask) {
            taskItem.getJlb().addMouseListener(new LabelEvent(taskItem.getStatus(), taskItem.getJlb(), listTask));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel temp;
        private String status;
        private JLabel jlb;
        private ArrayList<TaskItem> listTask;

        public LabelEvent(String status, JLabel jlb, ArrayList<TaskItem> listTask) {
            this.status = status;
            this.jlb = jlb;
            this.listTask = listTask;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (status) {
                
                case "DIEMHUONGDAN":
                    temp = new cuasoDiemHD();
                    break;
                case "DIEMPHANBIEN":
                    temp = new cuasoDiemPB();
                    break;
                case "DIEMTIEUBAN":
                    temp = new cuasoDiemTieuBan();
                    break;
                case "QLDOAN":
                    temp=new quanlyDoAn();
                    break;
                case "PHANDOAN":
                    temp=new cuasoDoan();
                    break;
            }
            jpnMain.removeAll();
            jpnMain.setLayout(new BorderLayout());
            jpnMain.add(temp);
            jpnMain.validate();
            jpnMain.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            statusSelected = status;
            System.out.println(statusSelected + "Press");
            jlb.setBackground(new Color(102, 102, 255));
            for (TaskItem taskItem : listTask) {
                if (!taskItem.getStatus().equalsIgnoreCase(statusSelected)) {
                    taskItem.getJlb().setBackground(new Color(204, 255, 255));
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println(statusSelected + "Enter");
            jlb.setBackground(new Color(204, 204, 255));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!statusSelected.equalsIgnoreCase(status)) {
                System.out.println(statusSelected + "exit");
                jlb.setBackground(new Color(204, 255, 255));
            }
        }
    }

}
