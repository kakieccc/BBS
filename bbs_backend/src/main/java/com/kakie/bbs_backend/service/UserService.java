package com.kakie.bbs_backend.service;

import com.kakie.bbs_backend.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 29967
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-01-21 19:23:08
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @return 新用户 id
     */
    long userRegister(User user,String checkPassword);

    /**
     * 用户登录
     * @return 脱敏后的用户信息
     */
    User userLogin(User user, HttpServletRequest request);
}
