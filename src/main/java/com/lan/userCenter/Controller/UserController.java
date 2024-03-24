package com.lan.userCenter.Controller;



import com.lan.userCenter.Common.ErrorCode;
import com.lan.userCenter.Common.ResultUtils;
import com.lan.userCenter.Exception.BusinessException;
import com.lan.userCenter.Model.Result;
import com.lan.userCenter.Model.constant.Constants;
import com.lan.userCenter.Model.domain.User;
import com.lan.userCenter.Model.request.UserLoginRequest;
import com.lan.userCenter.Model.request.UserRegisterRequest;
import com.lan.userCenter.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Slf4j
//@CrossOrigin(origins = "*")
public class UserController {

    @Resource
    private UserService userService;



    /**
     * 用户注册接口
     * @return 统一对象
     */
    @PostMapping("/register")
    public Result userRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws Exception {

            if (StringUtils.isAnyBlank(userRegisterRequest.getUserName(),userRegisterRequest.getUserAccount(),
                    userRegisterRequest.getUserPassword(),userRegisterRequest.getCheckPassword(),userRegisterRequest.getPlanetCode())){
                throw new BusinessException(ErrorCode.NOT_NULL_ERROR);
            }
            Long id = userService.userRegister(userRegisterRequest.getUserName(), userRegisterRequest.getUserAccount(),
                    userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword(),userRegisterRequest.getPlanetCode());

        return ResultUtils.success(ErrorCode.SUCCESS,id);
    }
    /**
     * 用户登录接口
     * @return 统一对象 Result<User>
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) throws Exception {
        if (StringUtils.isAnyBlank(userLoginRequest.getUserAccount(),userLoginRequest.getUserPassword())){
            throw new BusinessException(ErrorCode.NOT_NULL_ERROR);
        }
        User user = userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), session);

        return ResultUtils.success(user);
    }


    /**
     * 获得当前登录的用户信息
     * @param session
     * @return
     */
    @GetMapping("/currentUser")
    public Result<User> getCurrentUser(HttpSession session){
        User user =(User) session.getAttribute(Constants.USER_LOGIN_STATUS);
        if (user==null){
            throw new BusinessException(ErrorCode.NOT_ADMIN_ERROR,"不是管理员");
        }
        user = userService.getById(user.getId());
        user = userService.Masking(user);

        return ResultUtils.success(ErrorCode.SUCCESS,user);
    }
    /**
     * 查询接口
     * @param username
     * @param session
     * @return
     */
    @GetMapping("/search")
    public  Result search(String username,HttpSession session){
        List<User> user = userService.findUser(username, session);

        return ResultUtils.success(ErrorCode.SUCCESS,user);
    }

    /**
     * 删除接口
     * @param id
     * @return
     */
    @PostMapping ("/delete")
    public Result<Integer> deleteUser(Long id,HttpSession session){
        Integer result = userService.deleteById(id, session);

        return ResultUtils.success(ErrorCode.SUCCESS,result);
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public Result<Integer> userLogout(HttpSession session){
        if (session==null) throw new BusinessException(ErrorCode.NOT_AUTH_ERROR);
        int result = userService.userLogout(session);
        return ResultUtils.success(ErrorCode.SUCCESS,result);
    }
}
