/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acer
 */
public class SinhVien {
    public String MSSV;
    public String tenSV;
    public String lop;
    public String gioiTinh;
    public int xetDA;

    public SinhVien(String MSSV, String tenSV, String lop, String gioiTinh, int xetDA) {
        this.MSSV = MSSV;
        this.tenSV = tenSV;
        this.lop = lop;
        this.gioiTinh = gioiTinh;
        this.xetDA = xetDA;
    }

    public String getMSSV() {
        return MSSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public String getLop() {
        return lop;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public int getXetDA() {
        return xetDA;
    }
    
}
