package com.lan.userCenter;

import com.lan.userCenter.Mapper.UserMapper;
import com.lan.userCenter.Model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserCenterApplicationTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void userMapperTest(){
        User user = new User();
        user.setUserAccount("root3");
        user.setUserName("wen");
        user.setUserPassword("123456");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

}