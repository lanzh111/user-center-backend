package com.lan.userCenter.Service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lan.userCenter.Common.ErrorCode;
import com.lan.userCenter.Exception.BusinessException;
import com.lan.userCenter.Mapper.UserMapper;
import com.lan.userCenter.Model.constant.Constants;
import com.lan.userCenter.Model.domain.User;
import com.lan.userCenter.Service.UserService;
import com.lan.userCenter.utills.HmacSHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author lan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-03-08 16:33:17
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {



    @Resource
    private UserMapper userMapper;

    /**
     * @param username 用户名
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 二次密码
     * @return
     */
    @Override
    public Long userRegister(String username, String userAccount, String userPassword, String checkPassword,String planetCode) throws Exception {
        //非空
        if (StringUtils.isAnyBlank(username,userAccount,userPassword,checkPassword,planetCode))
            throw new BusinessException(ErrorCode.NOT_NULL_ERROR);
        //账号不小于6位
        if (userAccount.length()<4)
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号太短");
        //密码不小于8位
        if (userPassword.length()<8 || checkPassword.length()<8)
            throw new BusinessException(ErrorCode.PARAM_ERROR,"密码太短");
        //二次密码必须相同
        if (!userPassword.equals(checkPassword))
            throw new BusinessException(ErrorCode.PARAM_ERROR,"二次密码不同");
        //账号不能包含特殊字符
        String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！ @#￥%……&*（）——+|{}【】‘；：”“’。 ，、？ ]";
        Pattern p = Pattern.compile(regex);
        if (p.matcher(userAccount).find())  throw new BusinessException(ErrorCode.PARAM_ERROR,"账号不能包含特殊字符");
        //星球号不能大于6
        if (planetCode.length()>=6) throw new BusinessException(ErrorCode.PARAM_ERROR,"星球号太长");
        //账号不能重复
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,userAccount);
        int count = super.count(queryWrapper);
        if (count>0) throw new BusinessException(ErrorCode.PARAM_ERROR,"账号不能重复");
        //星球号不能重复
         queryWrapper =new LambdaQueryWrapper<>();
         queryWrapper.eq(User::getPlanetCode,planetCode);
         count = super.count(queryWrapper);
        if (count>0) throw new BusinessException(ErrorCode.PARAM_ERROR,"星球编号不能重复");
        //密码加密

        String newPassword = HmacSHA256Util.encryptHMAC2String(userPassword, Constants.SALT);

        //用户信息插入数据库
        User user = new User();
        user.setUserName(username);
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        user.setPlanetCode(planetCode);
        long id = userMapper.insert(user);
        if (id<=0) throw new RuntimeException("插入数据失败");
        return id;
    }

    /**
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword ,HttpSession httpSession) throws Exception {
        //非空
        if (StringUtils.isAnyBlank(userAccount,userPassword)) throw new BusinessException(ErrorCode.NOT_NULL_ERROR,"不能为空");
        //账号不小于6位
        if (userAccount.length()<4) throw  new BusinessException(ErrorCode.PARAM_ERROR,"账号太短");
        //密码不小于8位
        if (userPassword.length()<8 ) throw  new BusinessException(ErrorCode.PARAM_ERROR,"密码太短");

        //账号不能包含特殊字符
        String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！ @#￥%……&*（）——+|{}【】‘；：”“’。 ，、？ ]";
        Pattern p = Pattern.compile(regex);
        if (p.matcher(userAccount).find()) throw new BusinessException(ErrorCode.PARAM_ERROR,"不能包含特殊字符");

        //密码加密
        String newPassword = HmacSHA256Util.encryptHMAC2String(userPassword, Constants.SALT);
        //校验账号和密码是否正确
        LambdaQueryWrapper<User> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,userAccount);
        queryWrapper.eq(User::getUserPassword,newPassword);
        User user = super.getOne(queryWrapper);
        if (user==null){
            log.info(" user login filed, password cannot match");
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号或者密码错误");
        }
        //数据脱敏
        User masking = Masking(user);

        //用session记录用户状态
        httpSession.setAttribute(Constants.USER_LOGIN_STATUS,masking);
        return user;
    }



    /**
     *
     * @param username 用户名
     * @param session
     * @return
     */
    @Override
    public List<User> findUser(String username,HttpSession session) {
        //是否是管理员
        if (!isAdm(session)) throw new BusinessException(ErrorCode.PARAM_ERROR,"没有权限");

        //通过用户名进行模糊查询
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(User::getUserName,username);
        List<User> users;
        if(StringUtils.isBlank(username)){
            users = super.list();
        }else {
            users = super.list(wrapper);
        }

        users.stream().map(user ->{
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
        return users;
    }




    /**
     *
     * @param id
     * @param session
     * @return
     */
    @Override
    public Integer deleteById(Long id,HttpSession session) {
        //是否是管理员
        if (!isAdm(session)) throw new BusinessException(ErrorCode.NOT_ADMIN_ERROR);
        return  userMapper.deleteById(id);
    }


    /**
     * 数据脱敏
     * @param user
     */
    public User Masking(User user) {
        if (user==null){
            throw new BusinessException(ErrorCode.NOT_NULL_ERROR);
        }
        User u = new User();
        u.setId(user.getId());
        u.setUserName(user.getUserName());
        u.setSex(user.getSex());
        u.setPlanetCode(user.getPlanetCode());
        u.setAvatarUrl(user.getAvatarUrl());
        u.setPhone(user.getPhone());
        u.setUserRole(user.getUserRole());
        u.setUserAccount(user.getUserAccount());
        return u;
    }


    /**
     * 用户注销
     * @param session
     * @return
     */
    @Override
    public int userLogout(HttpSession session) {
        if (session==null) throw new BusinessException(ErrorCode.NOT_NULL_ERROR);
        session.removeAttribute(Constants.USER_LOGIN_STATUS);
        return 1;
    }

    /**
     * 是否是管理员
     * @param session
     * @return
     */
    private boolean isAdm(HttpSession session) {

        User u = (User) session.getAttribute(Constants.USER_LOGIN_STATUS);
        if (u==null) return true;
        if (u.getUserRole()==1) return true;
        return false;
    }
}




