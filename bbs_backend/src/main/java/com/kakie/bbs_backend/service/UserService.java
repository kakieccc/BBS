package com.kakie.bbs_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kakie.bbs_backend.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userName     用户名
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userName, String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户  search by SQL
     * @param tagList 用户对应的标签列表
     * @return
     */
    List<User> searchUserBySQL(List<String> tagList);

    /**
     * 根据标签搜索用户  search by json
     * @param tagList 用户对应的标签列表
     * @return
     */
    List<User> searchUserByTags(List<String> tagList);
}
