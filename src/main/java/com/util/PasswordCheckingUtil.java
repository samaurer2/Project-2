package com.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordCheckingUtil {

    public static boolean checkPass(String plainPass, String hashPass){
        if(BCrypt.checkpw(plainPass, hashPass)){
            System.out.println("The password matches.");
            return true;

        }else{
            System.out.println("The password does not match.");
            return false;
        }
    }
}
