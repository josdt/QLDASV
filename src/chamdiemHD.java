/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Administrator
 */
public class chamdiemHD {
    String MaDA;
    String MaSV;
    String MaGvHD;
    float DiemHD;

    public chamdiemHD() {
        this.MaDA = "";
        this.MaSV = "";
        this.MaGvHD = "";
        this.DiemHD = 0;
    }

    public chamdiemHD(String MaDA, String MaSV, String MaGvHD,  float DiemHD) {
        this.MaDA = MaDA;
        this.MaSV = MaSV;
        this.MaGvHD = MaGvHD;
        this.DiemHD = DiemHD;
    }

    public float getDiemHD() {
        return DiemHD;
    }
 
    public Object[] toObjectsdHD(){
    return new Object[]{MaDA, MaSV, MaGvHD, DiemHD,};
    }
 


    
}
