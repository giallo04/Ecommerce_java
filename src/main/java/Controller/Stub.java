package Controller;

public class Stub {
    public static String[][] getDati(){
        return new String[][]{{"Caramelle","213"},{"Maglia Napoli","1"},{"Biscotti","34"}};
    }
    public static boolean addProduct(String imgPath, String name, String price, String category){
        return true;
    }
    public String[] getUserInfo(){
        return new String[]{"Diego","Armando","dios@napoli","ForzaNapoli","Napoli","Giugliano in Campania","8123","via de Dios"};//STUB
    }
    public  String getUserImgUrl(){
        return  "/users"+getUserInfo()[2]+".png";
    }
}
