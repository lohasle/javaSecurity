package theDemo.security.hjsec;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import theDemo.security.rsa.GUIDGenerator;

public class SecurityUtil {
    private static final String RSA_PUBLIC_KEY = "RSAPublicKey";
    private static final String RSA_PRIVATE_KEY = "RSAPrivateKey";
    private static final String RSA_ALGORITHM = "RSA";
    
    /**
     * BASE64解密
     * base
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
    
    /**
     * 初始化密钥
     * 
     * @return
     * @throws Exception
     * 
     */
    public static Map<String, Object> initRsaKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(RSA_ALGORITHM);
        SecureRandom random = new SecureRandom();//加密随机数生成器 (RNG)
        random.setSeed(GUIDGenerator.instance().generate().getBytes());  //  设置随机种子
        keyPairGen.initialize(1024,random);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }
    
    
    
    
}
