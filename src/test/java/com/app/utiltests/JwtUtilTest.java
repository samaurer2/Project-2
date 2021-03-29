package com.app.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.exceptions.UserNotFoundException;
import com.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtUtilTest {


    @Test
    void creates_jwt_client(){
        String jwt = null;
        try {
            jwt = JwtUtil.generateJwtForClient("client", "password");
            Assertions.assertNotNull(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void creates_jwt_tech(){
        String jwt = null;
        try {
            jwt = JwtUtil.generateJwtForTech("admin", "password");
            Assertions.assertNotNull(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void decode_jwt(){

        DecodedJWT jwt = JwtUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiY2xpZW50IiwiaWQiOjEsInVzZXJOYW1lIjoiY2xpZW50In0.VrdTbpJPdnNHHEQh9b7Qgz2I4-UBiGbc2yoBwLwRbVg");
        String role = jwt.getClaim("role").asString();

        Assertions.assertEquals("client", role);
    }

}
