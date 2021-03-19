package com.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtUtil {


    private static final String secret ="super secret string no one else should ever have";
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);

    private static ClientService cserv;

    @Autowired
    private ClientService clientService;

    @PostConstruct
    private void init(){
        cserv = this.clientService;
    }

    public static String generateJwtForClient(String uName, String pPass){

        if(PasswordCheckingUtil.checkPass(pPass, cserv.getClient(uName).getPassword())){
//            String token = JWT.create()
//                    .withClaim("userName", uName)
//                    .withClaim("role", "client")
//                    .withClaim("id", cserv.getClient(uName).getId())
//                    .sign(algorithm);
            return JWT.create()
                    .withClaim("userName", uName)
                    .withClaim("role", "client")
                    .withClaim("id", cserv.getClient(uName).getId())
                    .sign(algorithm);
        }else{
            return null;
        }

    }


    public static DecodedJWT isValidJWT(String token){
        return JWT.require(algorithm).build().verify(token);
    }


}
