import org.junit.Test;
import security.command.DESCoder;

/**
 * Created by lohas on 16/7/14.
 */

public class TestDES {



    @Test
    public void test(){
        // 待加密内容
        String str = "测试内容";
        // 密码长度大于8
        String password = "12345678";
        System.out.println("加密内容: "+str);
        byte[] result = DESCoder.desCrypto(str.getBytes(), password);
        System.out.println("加密后内容为：" + new String(result));
        // 直接将如上内容解密
        try {
            byte[] decryResult = DESCoder.decrypt(result, password);
            System.out.println("解密后内容为：" + new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
