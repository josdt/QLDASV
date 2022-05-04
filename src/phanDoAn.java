/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class phanDoAn {
    String MaSV;
    String MagvHD;
    String MaDA;
    
    


    public phanDoAn() {
        this.MaSV="";
        this.MagvHD="";
        this.MaDA="";

    }

    public phanDoAn(String maSV,String maGVHD, String maDOAN) {
        this.MaSV = maSV;
        this.MagvHD=maGVHD;
        this.MaDA = maDOAN;
        
    }
    public Object[] toObjectsphanDA(){
    return new Object[]{MaSV,MagvHD, MaDA};
    }
}
