
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ChuanHoa {
    public String KiemTraMSV(String maSV){
        String pattern= "[Nn]{1}[0-9]{2}[Dd]{1}[Cc]{2}[Nn]{1}[0-9]{3}";
        if(maSV.matches(pattern)) return maSV.toUpperCase();
        else return "0";
    }
    public String KiemTraMDA(String maDA){
        String pattern= "[Dd]{1}[Oo]{1}[Aa]{1}[Nn]{1}[0-9]{3}";
        if(maDA.matches(pattern)) return maDA.toUpperCase();
        else return "0";
    }
    public String KiemTraMGV(String maGV){
        String pattern= "[Gg]{1}[Vv]{1}[0-9]{3}";
        if(maGV.matches(pattern)) return maGV.toUpperCase();
        else return "0";
    }
    public String KiemTraMHD(String maHD){
        String pattern= "[Hh]{1}[Dd]{1}[Bb]{1}[Cc]{1}[0-9]{2}";
        if(maHD.matches(pattern)) return maHD.toUpperCase();
        else return "0";
    }
    public String KiemTraMTB(String maTB){
        String pattern= "[Tt]{1}[Bb]{1}[0-9]{3}";
        if(maTB.matches(pattern)) return maTB.toUpperCase();
        else return "0";
    }
    public String KiemTraMaDN(String maDN){
        String pattern= "[Nn]{1}[0-9]{2}[Dd]{1}[Cc]{2}[Nn]{1}[0-9]{3}";
        String pattern2= "[Qq]{1}[Ll]{1}[0-9]{1}";
        String pattern3= "[Gg]{1}[Vv]{1}[0-9]{3}";
        if(maDN.matches(pattern) || maDN.matches(pattern2) || maDN.matches(pattern3)) return maDN.toUpperCase();
        else return "0";
    }
    public String KiemTraTenDA(String ten){
        String pattern= "[a-zA-Z ]+";
        if(ten.matches(pattern)) return ten.toUpperCase();
        else return "0";
    }
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                  // return pattern.matcher(temp).replaceAll("");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
    }
    public String PhanQuyenDN(String loai){
        String pattern= "[Nn]{1}[0-9]{2}[Dd]{1}[Cc]{2}[Nn]{1}[0-9]{3}";
        String pattern2= "[Aa]{1}[Dd]{1}[Mm]{1}[Ii]{1}[Nn]{1}";
        String pattern3= "[Gg]{1}[Vv]{1}[0-9]{3}";
        if(loai.matches(pattern)) return "SV";
        if(loai.matches(pattern2)) return "ADMIN";
        if(loai.matches(pattern3)) return "GV";
        return "0";
    }
    public String KiemTraTen(String ten){
        String pattern= "[a-zA-Z ]+";
        if(ten.matches(pattern)) return ChuanHoaTen(ten);
        else return "0";
    }
    public String KiemTraLop(String lop){
        String pattern= "[Dd]{1}[0-9]{2}[Cc]{1}[Qq]{1}[Cc]{1}[Nn]{1}[0]{1}[1-3]{1}[Nn]{1}";
        if(lop.matches(pattern)) return lop.toUpperCase();
        else return "0";
    }
    public String KiemTraMatKhau(String matKhau){
        String pattern= "[a-zA-Z0-9]{6,}";
        if(matKhau.matches(pattern)) return matKhau;
        else return "0";
    }
    public String ChuanHoaTen(String ten){
        ten= ten.trim();
        ten=ten.toLowerCase();
        ten= ten.replaceAll("\\s+", " ");
        String temp[]= ten.split(" ");
        ten="";
        for(int i=0;i<temp.length;i++){
            ten+=String.valueOf(temp[i].charAt(0)).toUpperCase()+ temp[i].substring(1);
            if(i< temp.length -1){
                ten+=" ";
            }
        }
        return ten;
    }
}
