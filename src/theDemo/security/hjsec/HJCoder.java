package theDemo.security.hjsec;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import theDemo.security.command.DESCoder;
import theDemo.security.command.RSACoder;

/**
 * 服务器上存公钥
 * 
 * @author 付乐
 * @createTime 2013-6-25
 */
public class HJCoder {
    
    public static final String DEFAULT_KEY_ALGORITHM = "RSA";
    
    private String             rsaPublicKey;
    
    private String             rsaPrivateKey;
    
    private String             desPassword;
    
    private void setRSAPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }
    
    public String getRSAPrivateKey() {
        return rsaPrivateKey;
    }
    
    private void setRSAPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }
    
    private void setDesPassword(String desPassword) {
        this.desPassword = desPassword;
    }
    
    private HJCoder() {
    }
    
    /**
     * 得到 HJ 加密工具
     * 
     * @param desPassword
     *            des 加密口令
     * @param keyAlgorithm
     *            非对称加密方法
     * @return
     * @throws Exception
     */
    public static HJCoder getInstance(String desPassword, String keyAlgorithm) throws Exception {
        HJCoder hjCoder = new HJCoder();
        Map<String, Object> map = RSACoder.initKey();
        hjCoder.setRSAPrivateKey(RSACoder.getPrivateKey(map));
        hjCoder.setRSAPublicKey(RSACoder.getPublicKey(map));
        hjCoder.setDesPassword(desPassword);
        return hjCoder;
    }
    
    public void saveKeysToFile(String basePath) {
        String[][] keys = { {"pub.data", rsaPublicKey }, {"pri.data", rsaPrivateKey } };
        for (int i = 0; i < 2; i++) {
            FileOutputStream fo = null;
            try {
                fo = new FileOutputStream(basePath + File.separator + keys[i][0]);
                PrintWriter prWriter = new PrintWriter(fo);
                prWriter.write(keys[i][1]);
                prWriter.flush();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                saveColeCloseable(fo);
            }
        }
    }
    
    private String readFileToString(String filePath) {
        FileReader fr = null;
        try {
            char data[] = new char[1024];
            fr = new FileReader(filePath);
            int num;
            StringBuffer sb = new StringBuffer();
            while ((num = fr.read(data)) != -1) {
                String str = new String(data, 0, num);
                sb.append(str);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            saveColeCloseable(fr);
        }
        return null;
    }
    
    private void saveColeCloseable(Closeable clo) {
        if (clo != null) {
            try {
                clo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 私钥加密
     * des rsa
     * @param str
     * @param rsaPrivateKey
     *            私钥字符串
     * @param desPassword
     *            des 口令
     * @return
     */
    public byte[] encrypt(String str, String rsaPrivateKey, String desPassword) {
        try {
            byte[] encodedData = DESCoder.desCrypto(str.getBytes(), desPassword);
            return RSACoder.encryptByPrivateKey(encodedData, rsaPrivateKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 数据用公钥加密
        return null;
    }
    
    
    /**
     * 公钥解密
     * 
     * @return
     */
    public static byte[] decrypt(byte[] encryptByte,String desPassword,String rsaPublicKey) {
        try {
            byte[] decodedData = RSACoder.decryptByPublicKey(encryptByte, rsaPublicKey);
            return DESCoder.decrypt(decodedData, desPassword);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 公钥解密
     * 
     * @return
     */
    public  byte[] decrypt(byte[] encryptByte) {
        try {
            byte[] decodedData = RSACoder.decryptByPublicKey(encryptByte, rsaPublicKey);
            return DESCoder.decrypt(decodedData, desPassword);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) throws Exception {
        String desPass = "huijinhuijin";
        try {
            HJCoder hjCoder = HJCoder.getInstance(desPass, HJCoder.DEFAULT_KEY_ALGORITHM);
            hjCoder.saveKeysToFile("C:\\D\\technology");
            String str = "aa中文";// 需要加密的字符
            System.out.println("加密之前： " + str);
            byte[] encodedData = hjCoder.encrypt(str,
                hjCoder.readFileToString("C:\\D\\technology\\pri.data"), desPass);
            System.out.println("------------开始加密----------");
            String encodedStr = new String(encodedData);
            System.out.println("加密之后: " + encodedStr);
            System.out.println("------------开始解密----------");
            byte[] decodedData = hjCoder.decrypt(encodedData);
            System.out.println("解密之后: " + new String(decodedData));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
