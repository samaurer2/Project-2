package com.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.services.ClientService;
import com.services.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtUtil {


    private static final String secret ="super secret string no one else should ever have";
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);

    private static ClientService cserv;
    private static TechnicianService tserv;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TechnicianService technicianService;

    @PostConstruct
    private void init(){
        cserv = this.clientService;
        tserv = this.technicianService;
    }

    public static String generateJwtForClient(String uName, String pPass){

        if(PasswordCheckingUtil.checkPass(pPass, cserv.getClient(uName).getPassword())){

            return JWT.create()
                    .withClaim("userName", uName)
                    .withClaim("role", "client")
                    .withClaim("id", cserv.getClient(uName).getId())
                    .sign(algorithm);
        }else{
            return null;
        }

    }

    public static String generateJwtForTech(String uName, String pPass){

        if(PasswordCheckingUtil.checkPass(pPass, tserv.getTech(uName).getPassword())){

            return JWT.create()
                    .withClaim("userName", uName)
                    .withClaim("role", tserv.getTech(uName).getType())
                    .withClaim("id", tserv.getTech(uName).getId())
                    .sign(algorithm);
        }else{
            return null;
        }

    }


    public static DecodedJWT isValidJWT(String token){
        return JWT.require(algorithm).build().verify(token);
    }


}