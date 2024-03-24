package com.lan.userCenter.Service.impl;

import com.lan.userCenter.Service.UserService;
import com.lan.userCenter.utills.HmacSHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@Slf4j
class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Test
    void StringutillsTest1(){
        System.out.println(StringUtils.isAnyBlank(null,"aa"));
    }
    @Test
    void regTest1(){
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！ @#￥%……&*（）——+|{}【】‘；：”“’。 ，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("s$tr@");
        log.info("reg={}",m.find());
    }

    @Test
    void userRegister() throws Exception {
        String username = "lan";
        String userAccount = "root";
        String password1 = "123";
        String password2 = "123";
        String p = "1";
        long i = userService.userRegister(username, userAccount, password1, password2,p);
        Assertions.assertSame(-1,i);

         username = "chen";
         userAccount = "root";
         password1 = "123456";
         password2 = "123456";

         i = userService.userRegister(username, userAccount, password1, password2,p);
        Assertions.assertSame(-1,i);


        username = "wen";
        userAccount = "root";
        password1 = "1234567";
        password2 = "12345699";

        i = userService.userRegister(username, userAccount, password1, password2,p);
        Assertions.assertSame(-1,i);

        username = "cent";
        userAccount = "root3";
        password1 = "12345678";
        password2 = "12345678";

        i = userService.userRegister(username, userAccount, password1, password2,p);
        Assertions.assertSame(-1,i);
    }


    @Test
    void RSATest() throws Exception {
        String encryptHMAC2String = HmacSHA256Util.encryptHMAC2String("123456", "lan");
        System.out.println(encryptHMAC2String);
    }
    @Test
    void userLogin() {

    }
}