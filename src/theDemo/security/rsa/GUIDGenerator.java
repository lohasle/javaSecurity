package theDemo.security.rsa;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 功能：生成GUID
 */
public class GUIDGenerator {
    
    private static SecureRandom  srand;
    
    private static String        s_id;
    
    private static GUIDGenerator instance = null;
    
    private GUIDGenerator() {
        srand = new SecureRandom();
        try {
            s_id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            s_id = "UnknownHost";
        }
    }
    
    public static synchronized GUIDGenerator instance() {
        if (instance == null)
            instance = new GUIDGenerator();
        
        return instance;
    }
    
    public String generate() throws NoSuchAlgorithmException {
        String valueBeforeMD5 = "";
        String valueAfterMD5 = "";
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();
        
        md5 = MessageDigest.getInstance("MD5");
        long time = System.currentTimeMillis();
        long rand = srand.nextLong();
        
        sbValueBeforeMD5.append(s_id);
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand));
        
        valueBeforeMD5 = sbValueBeforeMD5.toString();
        md5.update(valueBeforeMD5.getBytes());
        
        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < array.length; j++) {
            int b = array[j] & 0xFF;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }
        
        valueAfterMD5 = sb.toString();
        
        return format(valueAfterMD5);
    }
    
    public String format(String value) {
        String raw = value.toUpperCase();
        StringBuffer sb = new StringBuffer();
        sb.append(raw.substring(0, 8));
        sb.append("-");
        sb.append(raw.substring(8, 12));
        sb.append("-");
        sb.append(raw.substring(12, 16));
        sb.append("-");
        sb.append(raw.substring(16, 20));
        sb.append("-");
        sb.append(raw.substring(20));
        
        return sb.toString();
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(new GUIDGenerator().generate());
    }
}
