/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class chamdiemPB {
    String MaDA;
    String MaSV;
    String MaGvPB;
    float DiemPB;


    public chamdiemPB() {
        this.MaDA = "";
        this.MaSV = "";
        this.MaGvPB = "";
        this.DiemPB = 0;

    }

    public chamdiemPB(String MaDA, String MaSV, String MaGvPB,   float DiemPB) {
        this.MaDA = MaDA;
        this.MaSV = MaSV;
        this.MaGvPB = MaGvPB;
        this.DiemPB = DiemPB;
    }


    
    public Object[] toObjectsdPB(){
    return new Object[]{MaDA, MaSV, MaGvPB, DiemPB};
    }

    public float getDiemPB() {
        return DiemPB;
    }
    
}