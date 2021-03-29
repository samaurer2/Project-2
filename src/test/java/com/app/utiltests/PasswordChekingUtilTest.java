package com.app.utiltests;

import com.util.PasswordCheckingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordChekingUtilTest {

    @Test
    void check_password(){
        Assertions.assertTrue(PasswordCheckingUtil.checkPass("password", "$2a$04$.W5r140uCHkCl9W6M7G6c.DH8Oaj04tE/Kkew75hIUGCbwuingBAK"));
    }
}
