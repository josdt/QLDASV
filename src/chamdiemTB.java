/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Administrator
 */
public class chamdiemTB {
    String MaDA;
    String MaSV;
    String MaTieuBan;
    String MaGV;
    float DiemTieuBan;

    public chamdiemTB() {
        this.MaDA = "";
        this.MaSV = "";
        this.MaTieuBan = "";
        this.MaGV="";
        this.DiemTieuBan = 0;
    }

    public chamdiemTB(String MaDA, String MaSV, String MaTieuBan,String MaGV, float DiemTieuBan) {
        this.MaDA = MaDA;
        this.MaSV = MaSV;
        this.MaTieuBan = MaTieuBan;
        this.MaGV= MaGV;
        this.DiemTieuBan = DiemTieuBan;
    }
    
    public Object[] toObjectsdTB(){
    return new Object[]{MaDA, MaSV, MaTieuBan,MaGV, DiemTieuBan,};
    }

    public float getDiemTieuBan() {
        return DiemTieuBan;
    }
    
}
