import org.junit.Test;
import security.command.ShortUrl;

/**
 * Created by lohas on 16/7/14.
 */
public class TestShort {
    @Test
    public  void test(){
        String url = "http://www.sunchis.com";
        String[] shortText = ShortUrl.ShortText(url);
        String url2 = "";
        for(String string:shortText){
            System.out.println(string);
            url2+=string;
        }
    }
}
