package theDemo.security.command;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;


/**
 * RSA安全编码组件
 * 
 * @version 1.0
 * @since 1.0
 */
public abstract class RSACoderDemo extends Coder {
    public static final String KEY_ALGORITHM = "RSA";


    /**
     * 解密<br>
     * 用私钥解密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥  ASN.1 类型 PrivateKeyInfo 进行编码的专用密钥的 ASN.1 编码
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }



    /**
     * 加密<br>
     * 用公钥加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        //此类表示根据 ASN.1 类型 SubjectPublicKeyInfo 进行编码的公用密钥的 ASN.1 编码
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        //密钥（Key 类型的不透明加密密钥  <------->成密钥规范（底层密钥材料的透明表示）
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  //通过算法名 返回加密和解密提供密码功能的cipher
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);//用公钥初始化这个cipher

        return cipher.doFinal(data);
    } 




    //test
    public static void main(String[] args) throws Exception {
        String token = "admin/t"+new Date().getTime()+"/t123456";
        String pri = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMcKehpvUAUC9sVKY7ZCUQBEzPdg\n" +
                "wdbG5P/YsK6YTqxoTJWd25lwEAcmWKWd3ibqJsj913YEPKDoxpRT+iKsEioN6g/znzJ8LDmjHJvU\n" +
                "CoMaKM9TMs4LFP72OC4LQ1a7iUoBYturOnUG2o1kP6myZUSeFRjROtuX9rPYNvYgFRnxAgMBAAEC\n" +
                "gYBOmwqhyfgWIpeGTtyWLBaFPqabw7CJu77Qc6lDKo9Xc1FbfR4uuJz71d8IeRI02CA9HFKtHvs6\n" +
                "qPNTvN56Nu5szwGOj5L6sHKQOwNAGdq4/b9zbUp6tjdpwjQ+uwjihdwx9CmZaNpKOIO9z04cAXQ0\n" +
                "GFM9x8ftrY1dT9TPuTH0AQJBAOY6eBg2NXefcjAUtVQZT7uF0fkC+6w5zGJFIjgjrRN9EeAy1lHx\n" +
                "fqc4+sXlZbf28PLRy/lc4cmbLz2c3Op9j6ECQQDdUkkNx6SeBtkAEAAB+0Lp6wEuxBu3M6nO8IZN\n" +
                "f94NQ/yfH/E51Z9I6/M+Y/cwQYZdgY2otaAtt3TilnaFeahRAkEAljwlTjdhoJPtoa6BvUVfIFFs\n" +
                "+X+W14ZH9wCCGfw93u/IkHLcJFDggyLNkgHl5KxCA1IgNrMDq9mWdDA+gM46wQJAeXBFFYKOGQe4\n" +
                "2gfteSjdNmz0YSzJ6GQ4sIvZpXyb4ruDSezE9+73T5PoDDvdzmSlzXjgucmu8mbPg+T7c5GlgQJA\n" +
                "bCpQEUfrk7V3KF+7lh0zbC4OUcLczajbxbQZjdSeLE3JTpzNJIMbdzS3eeqN8Yvzu8xtaVZfyPJP\n" +
                "PYSr2rgQ9w==";
        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHCnoab1AFAvbFSmO2QlEARMz3YMHWxuT/2LCu\n" +
                "mE6saEyVnduZcBAHJlilnd4m6ibI/dd2BDyg6MaUU/oirBIqDeoP858yfCw5oxyb1AqDGijPUzLO\n" +
                "CxT+9jguC0NWu4lKAWLbqzp1BtqNZD+psmVEnhUY0Trbl/az2Db2IBUZ8QIDAQAB";
        System.out.println("公钥："+pub);
        System.out.println("私钥："+pri);
        System.out.println("---------->");

        System.out.println("加密前--->"+token);
        //公钥加密
        byte[] key1 = encryptByPublicKey(token.getBytes(),pub);
        String key1str = URLEncoder.encode(new String(Base64.encodeBytes(key1)));// base64 编码 + url 编码

        System.out.println("加密后--->"+key1str);

        /// 解密

        byte[] de_key_url = Base64.decode(URLDecoder.decode(key1str).getBytes());

        //私钥解密
        byte[] de_key =  decryptByPrivateKey(de_key_url,pri);


        System.out.println("解密后--->"+new String(de_key));

    }
}
