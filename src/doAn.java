/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class doAn {
    String MaDA;
    String TenDA;

    public doAn() {
        this.MaDA="";
        this.TenDA="";
    }

    public doAn(String MaDA, String TenDA) {
        this.MaDA = MaDA;
        this.TenDA = TenDA;
    }
    public Object[] toObjectsviewDA(){
        return new Object[]{MaDA, TenDA};
    }
}
