package com.util;

import org.mindrot.jbcrypt.BCrypt;

import java.util.logging.Logger;

public class PasswordCheckingUtil {

    public static boolean checkPass(String plainPass, String hashPass){
        if(BCrypt.checkpw(plainPass, hashPass)){
            return true;

        }else{
            return false;
        }
    }
}
