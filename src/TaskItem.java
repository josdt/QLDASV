/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class TaskItem {
    private String status;
    private JLabel jlb;

    public TaskItem(){
        
    }
    
    public TaskItem(String status, JLabel jlb) {
        this.status = status;
        this.jlb = jlb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JLabel getJlb() {
        return jlb;
    }

    public void setJlb(JLabel jlb) {
        this.jlb = jlb;
    }
    
}
