import org.junit.Test;
import security.command.Base64;
import security.command.RSACoder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * Created by lohas on 16/7/14.
 */
public class TestRsa {
    @Test
    public  void test() throws Exception {
        String token = "admin/t"+new Date().getTime()+"/t123456";
        Map myRsa = RSACoder.initKey();
        String pri = RSACoder.getPrivateKey(myRsa);
        String pub = RSACoder.getPublicKey(myRsa);
        System.out.println("公钥："+pub);
        System.out.println("私钥："+pri);
        System.out.println("---------->");

        System.out.println("加密前--->"+token);
        //公钥加密
        byte[] key1 = RSACoder.encryptByPublicKey(token.getBytes(),pub);
        String key1str = URLEncoder.encode(Base64.encodeBytes(key1));// base64 编码 + url 编码

        System.out.println("加密后--->"+key1str);

        /// 解密

        byte[] de_key_url = Base64.decode(URLDecoder.decode(key1str).getBytes());

        //私钥解密
        byte[] de_key =  RSACoder.decryptByPrivateKey(de_key_url,pri);


        System.out.println("解密后--->"+new String(de_key));
    }
}
