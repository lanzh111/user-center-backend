package com.lan.userCenter.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lan.userCenter.Model.domain.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
* @author lan
* @description 针对表【user】的数据库操作Service
* @createDate 2024-03-08 16:33:17
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     */
     Long userRegister(String username,String userAccount,String userPassword,String checkPassword,String planetCode) throws Exception;

    /**
     * 用户登录
     */
     User userLogin(String userAccount, String userPassword , HttpSession httpSession) throws Exception;
    /**
     * 用户查询
     */
     List<User> findUser(String username,HttpSession session);
    /**
     * 删除用户
     */
     Integer deleteById(Long id,HttpSession session);

    /**
     * 数据处理
     */
     User Masking(User user);
    /**
     * 用户注销
     */
    int userLogout(HttpSession session);
}
