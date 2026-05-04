import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    static String toHext(byte[] hash){
        StringBuilder hex = new StringBuilder();
        for(byte b:hash){
            String s = Integer.toHexString(0xff & b);
            if(s.length()==1){
                hex.append('0');
            }
            hex.append(s);
        }
        return hex.toString();
    }

    static String MD5(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte []hash = md.digest(input.getBytes());
            return toHext(hash);
        }catch (NoSuchAlgorithmException e){
            return "Error";
        }
    }

    static String SHA256(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            return toHext(hash);
        }catch (NoSuchAlgorithmException e){
            return "Error";
        }
    }

}
