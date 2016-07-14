import org.junit.Test;
import security.command.UcAuthCode;

import java.net.URLDecoder;

/**
 * Created by lohas on 16/7/14.
 */
public class UcTest {

    @Test
    public void test(){
        String key = "lohasle";
        String tokenValue = UcAuthCode.generateToken("banzhu","123456",key);
        System.out.println("--------encode:" + tokenValue);
        long lStart = System.currentTimeMillis();
        System.out.println("解码后：" + UcAuthCode.authcodeDecode(URLDecoder.decode(tokenValue), key));
        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加解密耗时：" + lUseTime + "毫秒");
    }
}
