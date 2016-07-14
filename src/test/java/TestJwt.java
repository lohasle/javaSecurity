
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lohasle on 2016/6/7.
 */
public class TestJwt {


    @Test
    public void test1() {

        Map map = new HashMap();
        map.put("1","1");
        map.put("2","1");

        Map head = new HashMap();
        head.put("11","11");
        head.put("12","11");

        String compact = Jwts.builder().setClaims(map).setHeader(head).compact();
        System.out.println(compact);

    }


    /**
     * header={typ=JWT, alg=HS256},body={version=1.0.0, api=member/getMember.do, issIp=10.211.55.2, uid=, exp=1467178905079},signature=mvoatWtoo6_Ws6yTxbDF_UnHYz2DdZVQ14RqksJYADQ
     */
    @Test
    public void test2() {

        String key = "7b940daa0d5b4b90840c4e5ec5d0652c";
        String str = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ2ZXJzaW9uIjoiMS4wLjAiLCJhcGkiOiJtZW1iZXIvZ2V0TWVtYmVyLmRvIiwiaXNzSXAiOiIxMC4yMTEuNTUuMiIsInVpZCI6IiIsImV4cCI6MTQ2NzE3ODkwNTA3OX0.mvoatWtoo6_Ws6yTxbDF_UnHYz2DdZVQ14RqksJYADQ";

        String encode = Base64Codec.BASE64.encode(key);

        try {
            Jwt jwt = Jwts.parser().setSigningKey(encode).parse(str);

            Map body = (Map) jwt.getBody();
            Object dateStr = body.get("exp");


            System.out.println(dateStr);

            Date endDate = new Date(Long.parseLong(dateStr.toString()));

            System.out.println(endDate);

            System.out.println(jwt);

//            assert endDate.after(new Date()); // 判断接口有效期
        } catch (SignatureException e) {

        }

    }
}