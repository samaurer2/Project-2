package com.app.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {


    @Test
    void creates_jwt_client(){
        String jwt = JwtUtil.generateJwtForClient("client", "password");
        System.out.println(jwt);
        Assertions.assertNotNull(jwt);
    }

    @Test
    void creates_jwt_tech(){
        String jwt = JwtUtil.generateJwtForTech("admin", "password");
        System.out.println(jwt);
        Assertions.assertNotNull(jwt);
    }

    @Test
    void decode_jwt(){

        DecodedJWT jwt = JwtUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiY2xpZW50IiwiaWQiOjEsInVzZXJOYW1lIjoiY2xpZW50In0.VrdTbpJPdnNHHEQh9b7Qgz2I4-UBiGbc2yoBwLwRbVg");
        System.out.println(jwt);
        String role = jwt.getClaim("role").asString();

        System.out.println(role);
        Assertions.assertEquals("client", role);
    }

}
